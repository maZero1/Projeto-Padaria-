package br.com.padaria.util;

import br.com.padaria.model.Cliente;
import br.com.padaria.model.Produto;

public class Renderers {
    public static String asString(Cliente c) {
        if (c == null) return "";
        return String.format("%d - %s (pontos: %d)", c.getId(), c.getNome(), c.getPontos());
    }
    public static String asString(Produto p) {
        if (p == null) return "";
        String flag = p.isResgatavel() ? "resgatável" : "normal";
        return String.format("%d - %s (R$ %.2f | estq: %d | %s | %d pts)",
            p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), flag, p.getCustoPontos());
    }
}
