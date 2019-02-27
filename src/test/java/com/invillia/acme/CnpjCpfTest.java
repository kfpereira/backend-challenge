package com.invillia.acme;

import com.invillia.acme.domain.utils.CnpjCpf;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class CnpjCpfTest {

    private static final String ESTE_CPF_DEVERIA_SER_VALIDO = "Este CPF deveria ser válido";
    private static final String ESTE_CPF_DEVERIA_SER_INVALIDO = "Este cpf deveria ser inválido";
    private static final String ESTE_CNPJ_DEVERIA_SER_VALIDO = "Este CNPJ deveria ser válido";
    private static final String ESTE_CNPJ_DEVERIA_SER_INVALIDO = "Este CNPJ deveria ser inválido";

    @Test
    void shouldBeValidate() {
        assertTrue(ESTE_CPF_DEVERIA_SER_VALIDO, CnpjCpf.validate("52998224725"));
    }

    @Test
    void shouldBeInvalidateByFirstNumber() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("52998224715"));
    }

    @Test
    void shouldBeInvalidateBySecondNumber() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("52998224729"));
    }

    @Test
    void shouldBeInvalidateSameNumber1() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("11111111111"));
    }

    @Test
    void shouldBeInvalidateSameNumber2() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("22222222222"));
    }

    @Test
    void shouldBeInvalidateSameNumber3() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("33333333333"));
    }

    @Test
    void shouldBeInvalidateSameNumber4() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("44444444444"));
    }

    @Test
    void shouldBeInvalidateSameNumber5() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("55555555555"));
    }

    @Test
    void shouldBeInvalidateSameNumber6() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("66666666666"));
    }

    @Test
    void shouldBeInvalidateSameNumber7() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("77777777777"));
    }

    @Test
    void shouldBeInvalidateSameNumber8() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("88888888888"));
    }

    @Test
    void shouldBeInvalidateSameNumber9() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("99999999999"));
    }

    @Test
    void shouldBeInvalidateNull() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate(null));
    }

    @Test
    void shouldBeInvalidateEmpty() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate(""));
    }

    @Test
    void shouldBeInvalidateWhiteSpaces() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("           "));
    }

    @Test
    void shouldBeInvalidateWhiteSpacesWithNumberAtFinal() {
        assertFalse(ESTE_CPF_DEVERIA_SER_INVALIDO, CnpjCpf.validate("          8"));
    }

    @Test
    void shouldBeValidateCnpj() {
        assertTrue(ESTE_CNPJ_DEVERIA_SER_VALIDO, CnpjCpf.validate("93587451000158"));
    }

    @Test
    void shouldBeValidateThisOneCnpj() {
        assertTrue(ESTE_CNPJ_DEVERIA_SER_VALIDO, CnpjCpf.validate("76173242000149"));
    }

    @Test
    void shouldBeInvalidateCnpjByFirstNumber() {
        assertFalse(ESTE_CNPJ_DEVERIA_SER_INVALIDO, CnpjCpf.validate("93587451000128"));
    }

    @Test
    void shouldBeInvalidateCnpjBySecondNumber() {
        assertFalse(ESTE_CNPJ_DEVERIA_SER_INVALIDO, CnpjCpf.validate("93587451000157"));
    }

    @Test
    void shouldBeInvalidateCnpjSameNumbers1() {
        assertFalse(ESTE_CNPJ_DEVERIA_SER_INVALIDO, CnpjCpf.validate("11111111111111"));
    }

    @Test
    void shouldBeInvalidateCnpjWhiteSpaces() {
        assertFalse(ESTE_CNPJ_DEVERIA_SER_INVALIDO, CnpjCpf.validate("              "));
    }

    @Test
    void shouldBeInvalidateCnpjWhiteSpacesWithNumberAtEnd() {
        assertFalse(ESTE_CNPJ_DEVERIA_SER_INVALIDO, CnpjCpf.validate("             8"));
    }

}