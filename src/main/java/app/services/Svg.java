package app.services;

public class Svg {

    private static final String SVG_TEMPLATE = "<svg version=\"1.1\"\n" +
            " x=\"%d\"y=\"%d\"\n" +
            " viewbox=\"%s\" width=\"%s\"\n" +
            " height=\"%s\" preserveAspectRatio=\"xMinYMin\">";


    private static final String ARROW_TEMPLATE = "<defs>\n" +
            "        <marker id=\"beginArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"0\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,6 L12,0 L12,12 L0,6\" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "        <marker id=\"endArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"12\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "    </defs>";

    private static final String LINE_TEMPLATE="<line x1=\"%d\"  y1=\"%d\" x2=\"%d\"   y2=\"%d\" \n" +
            " style=\"stroke: #006600;\n" +
            " marker-start: url(#beginArrow);\n" +
            " marker-end: url(#endArrow);\" />";

    private static final String RECTANGLE_TEMPLATE="<rect x=\"%.2f\" y=\"%.2f\" height=\"%f\" width=\"%f\" style=\"%s\"/>";

    private static final String SVG_TEXT_TEMPLATE="<text x=\"%d\" y=\"%d\" transform=\"rotate(%d %d %d)\">%s</text>";
    private StringBuilder svg = new StringBuilder();

    public Svg(int x, int y, String viewBox, String width, String length){

        svg.append(String.format(SVG_TEMPLATE, x, y, viewBox, width, length));
        svg.append(ARROW_TEMPLATE);
        svg.append(SVG_TEXT_TEMPLATE);

    }

    public void addRectangle(double x, double y, double length, double width, String style){

            svg.append(String.format(RECTANGLE_TEMPLATE, x, y, length, width, style));
    }

    public void addLine(int x1, int y1, int x2, int y2, String style){

        svg.append(String.format(LINE_TEMPLATE, x1, y1, x2, y2, style));

    }

    public void addArrow(int x1, int y1, int x2, int y2, String style)
    {
        svg.append(String.format(LINE_TEMPLATE, x1, y1, x2, y2, style));
    }

    public String addSvg(Svg innerSvg)
    {
        return svg.append(innerSvg.toString()).toString();
    }

    public void addText(int x, int y, int rotation,String text){
        svg.append(String.format(SVG_TEXT_TEMPLATE, x, y, rotation,x,y, text));

    }

    @Override
    public String toString() {
        return svg.append("</svg>").toString();
    }
}