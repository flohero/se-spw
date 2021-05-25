package spw4.tsp;

import java.io.*;
import java.util.*;

public class TSPLibParser {
    final static int EOF = 0;
    final static int NAME = 1;
    final static int TYPE = 2;
    final static int DIM = 3;
    final static int WEIGHTTYPE = 4;
    final static int NODETYPE = 5;
    final static int NODESECTION = 6;

    private BufferedReader source;
    private String instanceName;
    private double[][] vertices;
    private int weightType;

    TSPLibParser(String filename) throws IllegalArgumentException, IOException {
        if ((filename == null) || filename.isEmpty()) throw new IllegalArgumentException("filename must not be null or empty");
        if (!filename.endsWith(".tsp")) throw new IllegalArgumentException("filename has to be in TSP format (*.tsp)");

        File f = new File(filename);

        if (!f.exists())
            throw new IllegalArgumentException("file \"" + filename + "\" not found");

        if (!f.isFile())
            throw new IllegalArgumentException("\"" + filename + "\" is not a file");

        source = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

        instanceName = filename;
        vertices = null;
        weightType = -1;
    }

    public String getName() { return instanceName; }
    public double[][] getVertices() { return vertices; }
    public int getWeightType() { return weightType; }

    public void parse() throws IOException, TSPLibFormatException {
        int section = -1;
        String str = null;
        boolean typeIsChecked = false;
        boolean weightTypeIsChecked = false;

        do {
            str = source.readLine();
            section = getSection(str);

            if (section != -1) {
                switch (section) {
                    case NAME:
                        readName(str);
                        break;
                    case TYPE:
                        checkType(str);
                        typeIsChecked = true;
                        break;
                    case DIM:
                        initVerticesArray(str);
                        break;
                    case WEIGHTTYPE:
                        readWeightType(str);
                        weightTypeIsChecked = true;
                        break;
                    case NODETYPE:
                        checkNodeType(str);
                        break;
                    case NODESECTION:
                        readVertices();
                        break;
                }
            }
        } while (!((section == EOF) || (str == null)));

        if (!(typeIsChecked && weightTypeIsChecked))
            throw new TSPLibFormatException("file contains unknown (edge) types");
    }

    private int getSection(String str) throws IOException {
        if (str == null)
            return EOF;

        StringTokenizer tokenizer = new StringTokenizer(str, " :");

        if (!tokenizer.hasMoreTokens())
            return -1;

        String firstToken = tokenizer.nextToken();

        if (firstToken.equalsIgnoreCase("eof"))
            return EOF;
        if (firstToken.equalsIgnoreCase("name"))
            return NAME;
        if (firstToken.equalsIgnoreCase("type"))
            return TYPE;
        if (firstToken.equalsIgnoreCase("dimension"))
            return DIM;
        if (firstToken.equalsIgnoreCase("edge_weight_type"))
            return WEIGHTTYPE;
        if (firstToken.equalsIgnoreCase("node_coord_type"))
            return NODETYPE;
        if (firstToken.equalsIgnoreCase("node_coord_section"))
            return NODESECTION;

        return -1;
    }

    private void readName(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            instanceName = tokenizer.nextToken().trim();
        }
    }

    protected void checkType(String str) throws TSPLibFormatException {
        StringTokenizer tokenizer = new StringTokenizer(str, ": ");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String type = tokenizer.nextToken();
            if (!type.equalsIgnoreCase("tsp"))
                throw new TSPLibFormatException("input data format is not \"TSP\"");
        } else throw new TSPLibFormatException("unknown input data format");
    }

    private void initVerticesArray(String str) throws TSPLibFormatException {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String dimension = tokenizer.nextToken();
            dimension = dimension.trim();

            int dim = 0;
            try {
                dim = Integer.valueOf(dimension).intValue();
            } catch (NumberFormatException e) {
                throw new TSPLibFormatException("could not parse dimension: " + e.getMessage());
            }
            vertices = new double[dim][2];
        } else throw new TSPLibFormatException("dimension not found");
    }

    private void readWeightType(String str) throws TSPLibFormatException {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String type = tokenizer.nextToken();
            type = type.trim();

            if (type.equalsIgnoreCase("euc_2d"))
                weightType = 0;
            else if (type.equalsIgnoreCase("geo"))
                weightType = 1;
            else
                throw new TSPLibFormatException("unsupported type of edge weights");
        } else throw new TSPLibFormatException("edge weights type not found");
    }

    private void checkNodeType(String str) throws TSPLibFormatException {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String type = tokenizer.nextToken();
            type = type.trim();

            if (!type.equalsIgnoreCase("twod_coords"))
                throw new TSPLibFormatException("unsupported node type");
        }
    }

    private void readVertices() throws TSPLibFormatException {
        if (vertices == null)
            throw new TSPLibFormatException("dimension not found");

        try {
            for (int i = 0; i < vertices.length; i++) {
                String str = source.readLine();
                StringTokenizer tokenizer = new StringTokenizer(str);
                while (!tokenizer.hasMoreTokens()) {
                    str = source.readLine();
                    tokenizer = new StringTokenizer(str);
                }

                tokenizer.nextToken();
                vertices[i][0] = Double.valueOf(tokenizer.nextToken()).doubleValue();
                vertices[i][1] = Double.valueOf(tokenizer.nextToken()).doubleValue();

                if (tokenizer.hasMoreTokens())
                    throw new TSPLibFormatException("invalid node format");
            }
        } catch (IOException e) {
            throw new TSPLibFormatException("could not read node data: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new TSPLibFormatException("could not parse node data: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new TSPLibFormatException("missing node data: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new TSPLibFormatException("missing nodes: " + e.getMessage());
        }
    }
}
