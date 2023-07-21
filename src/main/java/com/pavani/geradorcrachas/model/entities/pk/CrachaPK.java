package com.pavani.geradorcrachas.model.entities.pk;

import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CrachaPK implements Serializable {
    @OneToOne
    @JoinColumn(name = "crachaFuncionario_id")
    private CrachaFuncionario crachaFuncionario;
    @ManyToOne
    @JoinColumn(name = "layout_id")
    private LayoutCracha layout;

    public CrachaPK() {
    }

    public CrachaPK(CrachaFuncionario crachaFuncionario, LayoutCracha layout) {
        this.crachaFuncionario = crachaFuncionario;
        this.layout = layout;
    }

    public CrachaFuncionario getCrachaFuncionario() {
        return crachaFuncionario;
    }

    public void setCrachaFuncionario(CrachaFuncionario crachaFuncionario) {
        this.crachaFuncionario = crachaFuncionario;
    }

    public LayoutCracha getLayout() {
        return layout;
    }

    public void setLayout(LayoutCracha layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrachaPK crachaPK = (CrachaPK) o;
        return Objects.equals(crachaFuncionario, crachaPK.crachaFuncionario) && Objects.equals(layout, crachaPK.layout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crachaFuncionario, layout);
    }
}
