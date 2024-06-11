import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

class GraphPanel extends JPanel {
    private Grafo grafo;
    private int newEdgeStart, newEdgeEnd;
    private boolean hasNewEdge;

    public GraphPanel(Grafo grafo) {
        this.grafo = grafo;
        this.hasNewEdge = false;
    }

    public void setNewEdge(int start, int end) {
        this.newEdgeStart = start;
        this.newEdgeEnd = end;
        this.hasNewEdge = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int radius = 20;

        // Desenha as arestas
        Vertice[] listaAdj = grafo.getListaAdj();
        for (int i = 0; i < listaAdj.length; i++) {
            Point start = grafo.getPosicaoVertice(i);
            Vertice atual = listaAdj[i];
            while (atual != null) {
                Point end = grafo.getPosicaoVertice(atual.vertice);
                if (hasNewEdge && i == newEdgeStart && atual.vertice == newEdgeEnd) {
                    g.setColor(Color.RED); // Aresta mais recente em vermelho
                } else {
                    g.setColor(Color.BLACK);
                }
                g.drawLine(start.x + radius / 2, start.y + radius / 2, end.x + radius / 2, end.y + radius / 2);

                // Desenha o peso da aresta
                int midX = (start.x + end.x) / 2;
                int midY = (start.y + end.y) / 2;
                g.drawString(String.valueOf(atual.peso), midX, midY);

                atual = atual.prox;
            }
        }

        // Desenha os vértices
        for (int i = 0; i < grafo.getQtdVertices(); i++) {
            Point pos = grafo.getPosicaoVertice(i);
            if (pos != null) {
                g.setColor(Color.BLUE);
                g.fillOval(pos.x, pos.y, radius, radius);
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(i), pos.x + radius / 2 - 5, pos.y + radius / 2 + 5);
            }
        }
    }
}
 