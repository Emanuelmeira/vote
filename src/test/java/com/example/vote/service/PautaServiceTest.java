package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.dto.DataToOpenSession;
import com.example.vote.domain.enums.Message;
import com.example.vote.domain.exception.BusinessException;
import com.example.vote.repository.PautaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    //EXEMPLOS DE TESTES UNITARIOS COBRINDO ALGUNS CENARIOS

    @InjectMocks
    private PautaService pautaService;
    @Mock
    private PautaRepository pautaRepository;

    @Test
    public void open_session_throw_PAUTA_WAS_OPEN(){

        var pauta = new Pauta();
        pauta.setOpen(true);

        var dataToOpenSession = new DataToOpenSession();

        when(pautaRepository.findById(any())).thenReturn(Optional.of(pauta));

        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> pautaService.openSession(1L, dataToOpenSession)
        );

        assertThat( businessException.getMessage()).isEqualTo(Message.PAUTA_WAS_OPEN.getMessage());
    }

    @Test
    public void open_session_success_default_time(){

        var pauta = new Pauta();
        pauta.setOpen(false);
        var dataToOpenSession = new DataToOpenSession();

        when(pautaRepository.findById(any())).thenReturn(Optional.of(pauta));

        pautaService.openSession(1L, dataToOpenSession);

        assertThat( pauta.isOpen()).isEqualTo(true);
    }

    @Test
    public void save_pauta_success(){
        var pauta = new Pauta();
        var pautaReturn = new Pauta();
        pautaReturn.setId(1L);

        when(pautaRepository.save(any())).thenReturn(pautaReturn);
        pautaService.save(pauta);

        assertThat( pautaReturn.getId()).isEqualTo(1L);
    }

    @Test
    public void generate_result_PAUTA_IS_OPEN(){

        var pauta = new Pauta();
        pauta.setOpen(true);
        pauta.setSessionStartedTime(LocalDateTime.now());
        pauta.setEndsIn(5L);

        when(pautaRepository.findById(any())).thenReturn(Optional.of(pauta));

        BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> pautaService.generateResult(1L)
        );

        assertThat( businessException.getMessage()).isEqualTo(Message.PAUTA_IS_OPEN.getMessage());
    }

}
