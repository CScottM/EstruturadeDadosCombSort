import java.util.ArrayList;
import java.util.Collections; // Mantido apenas caso queira usar em outro lugar, mas não é usado na opção 3
import java.util.Random;
import javax.swing.JOptionPane;

public class Principal {

    static ArrayList<Integer> listaOriginal = new ArrayList<>();
    static ArrayList<Integer> listaTrabalho = new ArrayList<>();

    // Para listas aleatórias reprodutíveis na apresentação
    static final long RANDOM_SEED = 42L;

    public static void main(String[] args) {

        String opcao = "";

        while (!"7".equals(opcao)) {
            opcao = JOptionPane.showInputDialog(
                    "===== MENU - COMB SORT vs BUBBLE SORT =====\n" +
                    "1 - Preencher Lista (conjunto fixo do professor)\n" +
                    "2 - Ordenar com Comb Sort (Alg 1)\n" +
                    "3 - Ordenar com Bubble Sort (Alg 2)\n" + // ALTERADO AQUI
                    "4 - Mostrar Lista Ordenada\n" +
                    "5 - Mostrar Lista Original\n" +
                    "6 - Mostrar Tempos (ns)\n" +
                    "7 - Sair\n" +
                    "8 - Preencher Lista com 5.000 números aleatórios\n" +
                    "9 - Incluir até 10 números informados pelo usuário"
            );

            if (opcao == null) break;

            switch (opcao) {
                case "1":
                    preencherLista();
                    break;
                case "2":
                    ordenarCombSort();
                    break;
                case "3":
                    ordenarBubbleSort(); // ALTERADO AQUI
                    break;
                case "4":
                    mostrar("Lista Ordenada", listaTrabalho);
                    break;
                case "5":
                    mostrar("Lista Original", listaOriginal);
                    break;
                case "6":
                    medirTempos();
                    break;
                case "7":
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    break;
                case "8":
                    preencherListaGrande5000();
                    break;
                case "9":
                    incluirAteDezNumerosUsuario();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    // ================================================================
    // 1) Preencher lista com os dados fixos do professor
    // ================================================================
    public static void preencherLista() {
        listaOriginal.clear();

        int[] dados = {
            432,809,213,725,37,960,578,63,921,145,689,281,506,955,194,374,820,62,
            890,485,786,911,394,178,627,902,420,579,733,96,311,654,250,771,9,624,
            712,135,505,884,445,688,77,912,721,390,538,893,470,679,1,869,302,946,
            561,144,790,422,769,57,899,308,687,469,237,630,961,36,578,799,180,628,
            886,298,835,62,974,214,518,746,132,899,243,511,798,235,681,61,918,375,
            692,993,183,553,846,24,954,286,647,129
        };

        for (int v : dados) {
            listaOriginal.add(v);
        }

        listaTrabalho = new ArrayList<>(listaOriginal);

        JOptionPane.showMessageDialog(null, "Lista preenchida com " + listaOriginal.size() + " elementos!");
    }

    // ================================================================
    // 8) Preencher com 5.000 números aleatórios (substitui a lista)
    // ================================================================
    public static void preencherListaGrande5000() {
        listaOriginal.clear();
        Random rnd = new Random(RANDOM_SEED);
        final int N = 5000;
        final int MIN = 0;
        final int MAX = 100_000; // valores na faixa [0, 100000)

        for (int i = 0; i < N; i++) {
            listaOriginal.add(rnd.nextInt(MAX - MIN) + MIN);
        }

        listaTrabalho = new ArrayList<>(listaOriginal);
        JOptionPane.showMessageDialog(null, "Lista aleatória preenchida com " + N + " elementos!");
    }

    // ================================================================
    // 9) Incluir até 10 números informados pelo usuário (acrescenta)
    // ================================================================
    public static void incluirAteDezNumerosUsuario() {
        String entrada = JOptionPane.showInputDialog(
                "Digite até 10 números inteiros separados por vírgula.\n" +
                "Ex.: 12, 7, -3, 99");

        if (entrada == null || entrada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum número inserido.");
            return;
        }

        String[] partes = entrada.split(",");
        int adicionados = 0;

        for (int i = 0; i < partes.length && adicionados < 10; i++) {
            String p = partes[i].trim();
            if (p.isEmpty()) continue;
            try {
                int valor = Integer.parseInt(p);
                listaOriginal.add(valor);
                adicionados++;
            } catch (NumberFormatException e) {
                // ignora tokens inválidos
            }
        }

        if (adicionados == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum inteiro válido foi inserido.");
            return;
        }

        // sempre que a original mudar, a de trabalho é resetada
        listaTrabalho = new ArrayList<>(listaOriginal);
        JOptionPane.showMessageDialog(null, "Foram adicionados " + adicionados + " número(s) à lista.");
    }

    // ================================================================
    // 2) Ordenar com Comb Sort
    // ================================================================
    public static void ordenarCombSort() {
        if (listaOriginal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a lista primeiro!");
            return;
        }

        listaTrabalho = new ArrayList<>(listaOriginal);
        combSort(listaTrabalho);

        JOptionPane.showMessageDialog(null, "Lista ordenada com Comb Sort!");
    }

    // ================================================================
    // 3) Ordenar com Bubble Sort (SUBSTITUIU COLLECTIONS)
    // ================================================================
    public static void ordenarBubbleSort() {
        if (listaOriginal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a lista primeiro!");
            return;
        }

        listaTrabalho = new ArrayList<>(listaOriginal);
        bubbleSort(listaTrabalho); // Chama o novo método Bubble Sort

        JOptionPane.showMessageDialog(null, "Lista ordenada com Bubble Sort!");
    }

    // ================================================================
    // 4 e 5) Exibição
    // ================================================================
    public static void mostrar(String titulo, ArrayList<Integer> lista) {
        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista vazia. Preencha primeiro.");
            return;
        }

        String conteudo;
        int maxPreview = 50;
        if (lista.size() <= maxPreview) {
            conteudo = lista.toString();
        } else {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < maxPreview; i++) {
                sb.append(lista.get(i));
                if (i < maxPreview - 1) sb.append(", ");
            }
            sb.append(", ... (total ").append(lista.size()).append(")]");
            conteudo = sb.toString();
        }

        JOptionPane.showMessageDialog(null, titulo + ":\n" + conteudo);
        System.out.println(titulo + ": " + conteudo);
    }

    // ================================================================
    // 6) Tempo de execução
    // ================================================================
    public static void medirTempos() {
        if (listaOriginal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a lista primeiro!");
            return;
        }

        ArrayList<Integer> lista1 = new ArrayList<>(listaOriginal);
        ArrayList<Integer> lista2 = new ArrayList<>(listaOriginal);

        // Comb Sort
        long t1 = System.nanoTime();
        combSort(lista1);
        long t2 = System.nanoTime();
        long tempoComb = t2 - t1;

        // Bubble Sort (Mudança aqui: mede o Bubble Sort agora)
        long t3 = System.nanoTime();
        bubbleSort(lista2);
        long t4 = System.nanoTime();
        long tempoBubble = t4 - t3;

        String msg =
                "===== TEMPOS EM NANOSEGUNDOS =====\n\n" +
                "Tamanho da lista: " + listaOriginal.size() + "\n" +
                "Comb Sort:    " + String.format("%,d", tempoComb) + " ns\n" +
                "Bubble Sort:  " + String.format("%,d", tempoBubble) + " ns\n\n" +
                "(Bubble Sort é geralmente MUITO mais lento em listas grandes)";

        JOptionPane.showMessageDialog(null, msg);
        System.out.println(msg);
    }

    // ================================================================
    // IMPLEMENTAÇÃO DO COMB SORT
    // ================================================================
    public static void combSort(ArrayList<Integer> lista) {
        int n = lista.size();
        int gap = n;
        boolean houveTroca = true;
        final double fator = 1.3;

        while (gap > 1 || houveTroca) {
            gap = (int)(gap / fator);
            if (gap < 1) gap = 1;

            houveTroca = false;

            for (int i = 0; i + gap < n; i++) {
                if (lista.get(i) > lista.get(i + gap)) {
                    int temp = lista.get(i);
                    lista.set(i, lista.get(i + gap));
                    lista.set(i + gap, temp);
                    houveTroca = true;
                }
            }
        }
    }

    // ================================================================
    // IMPLEMENTAÇÃO DO BUBBLE SORT (NOVO)
    // ================================================================
    public static void bubbleSort(ArrayList<Integer> lista) {
        int n = lista.size();
        boolean trocou;
        
        for (int i = 0; i < n - 1; i++) {
            trocou = false;
            // O laço vai até n - i - 1 porque os últimos i elementos já estão ordenados
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j) > lista.get(j + 1)) {
                    // Troca
                    int temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                    trocou = true;
                }
            }
            // Se não houve troca nesta passagem, a lista já está ordenada
            if (!trocou) {
                break;
            }
        }
    }
}