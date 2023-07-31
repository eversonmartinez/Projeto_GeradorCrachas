package com.pavani.geradorcrachas.model.entities;

import com.pavani.geradorcrachas.model.entities.pk.CrachaPK;
import com.pavani.geradorcrachas.model.exceptions.GeradorCrachaException;
import com.pavani.geradorcrachas.service.GeradorCrachaService;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Cracha implements Serializable {

    @EmbeddedId
    private CrachaPK id = new CrachaPK();

    @Lob
    private byte[] crachaFinalizado;

    public Cracha(){}

    public Cracha(CrachaFuncionario crachaFuncionario, LayoutCracha layout) throws GeradorCrachaException, IOException {
        this.id.setCrachaFuncionario(crachaFuncionario);
        this.id.setLayout(layout);
        gerarCrachaFinalizado();
    }

    public void gerarCrachaFinalizado() throws GeradorCrachaException, IOException {
            GeradorCrachaService service = new GeradorCrachaService(getLayout());
            crachaFinalizado = service.gerarCracha(getCrachaFuncionario());
    }

    public CrachaPK getId() {
        return id;
    }

    public CrachaFuncionario getCrachaFuncionario() {
        return id.getCrachaFuncionario();
    }

    public LayoutCracha getLayout(){
        return id.getLayout();
    }

    public byte[] getCrachaFinalizado() {
        return crachaFinalizado;
    }

    public void setCrachaFinalizado(byte[] crachaFinalizado) {
        this.crachaFinalizado = crachaFinalizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cracha cracha = (Cracha) o;
        return Objects.equals(id, cracha.id) && Arrays.equals(crachaFinalizado, cracha.crachaFinalizado);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(crachaFinalizado);
        return result;
    }
}
