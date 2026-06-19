package com.teste_aprendizagem.spring_frete.model.entity.enums;

public enum CargaTipo {
    /** Cargas perigosas que exigem manuseio especial. */
    INFLAMAVEL,
    /** Objetos quebráveis que demandam cuidado extra. */
    FRAGIL,
    /** Mercadorias de alto valor com grande risco de roubo (eletrônicos, etc). */
    VISADO,
    /** Cargas padrão sem necessidades específicas de segurança. */
    COMUM;
}
