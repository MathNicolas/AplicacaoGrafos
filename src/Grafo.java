import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class Grafo {
    private Vertice[] listaAdj;
    private int qtdVertices;
    private Map<Integer, Point> posicoesVertices;

    public Grafo(int qtdVertices) {
        this.qtdVertices = qtdVertices;
        this.listaAdj = new Vertice[qtdVertices];
        this.posicoesVertices = new HashMap<>();
    }

    public void adicionaAresta(int vI, int vF, int peso) {
        Vertice novoVertice = new Vertice(vF, peso);
        novoVertice.prox = listaAdj[vI];
        listaAdj[vI] = novoVertice;
    }

    public void setPosicaoVertice(int vertice, int x, int y) {
        posicoesVertices.put(vertice, new Point(x, y));
    }

    public Point getPosicaoVertice(int vertice) {
        return posicoesVertices.get(vertice);
    }

    public Vertice[] getListaAdj() {
        return listaAdj;
    }

    public int getQtdVertices() {
        return qtdVertices;
    }

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder();
        for (int i = 0; i < listaAdj.length; i++) {
            grafo.append(String.format("V%d-> ", i));
            Vertice atual = listaAdj[i];
            while (atual != null) {
                grafo.append(String.format("%d(%d)-> ", atual.vertice, atual.peso));
                atual = atual.prox;
            }
            grafo.append("\n");
        }
        return grafo.toString();
    }

    public static void main(String[] args) {
        int qtdVertices = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de vértices:"));
        Grafo grafo = new Grafo(qtdVertices);

        JFrame frame = new JFrame("Construção do Grafo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        GraphPanel graphPanel = new GraphPanel(grafo);
        frame.add(graphPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        JTextField vIField = new JTextField(5);
        JTextField vFField = new JTextField(5);
        JTextField pesoField = new JTextField(5);
        JButton addButton = new JButton("Adicionar Aresta");

        inputPanel.add(new JLabel("Vértice Inicial:"));
        inputPanel.add(vIField);
        inputPanel.add(new JLabel("Vértice Final:"));
        inputPanel.add(vFField);
        inputPanel.add(new JLabel("Peso:"));
        inputPanel.add(pesoField);
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                int vI = Integer.parseInt(vIField.getText());
                int vF = Integer.parseInt(vFField.getText());
                int peso = Integer.parseInt(pesoField.getText());

                grafo.adicionaAresta(vI, vF, peso);
                graphPanel.setNewEdge(vI, vF);
                graphPanel.repaint();

                vIField.setText("");
                vFField.setText("");
                pesoField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.");
            }
        });

        frame.setVisible(true);

        // Adiciona uma etapa para definir as posições dos vértices
        JOptionPane.showMessageDialog(frame, "Clique na tela para definir as posições dos vértices.");

        graphPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            private int verticeAtual = 0;

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (verticeAtual < qtdVertices) {
                    grafo.setPosicaoVertice(verticeAtual, e.getX(), e.getY());
                    verticeAtual++;
                    graphPanel.repaint();
                }
            }
        });
    }
}
 