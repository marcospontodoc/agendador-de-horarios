package marcos.doc.agendamento_horario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import marcos.doc.agendamento_horario.infrastructure.entity.Agendamento;
import marcos.doc.agendamento_horario.infrastructure.repository.AgendamentoRepository;
import marcos.doc.agendamento_horario.services.AgendamentoService;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private Agendamento criarAgendamento(){
        Agendamento agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setCliente("Marcos");
        agendamento.setServico("Corte de cabelo");
        agendamento.setProfissional("James");
        agendamento.setTelefoneCliente("123456789");
        agendamento.setDataHoraAgendamento(LocalDateTime.of(2024, 6, 15, 10, 0));
        return agendamento;
    }
    
    @Test
    public void salvarAgendamentoComSucesso(){
        Agendamento agendamento = criarAgendamento();
        
        when(agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(
                any(),
                any(),
                any()))
                .thenReturn(null);
        
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        Agendamento agendamentoSalvo = agendamentoService.salvarAgendamento(agendamento);
        assertNotNull(agendamentoSalvo);
        assertEquals("Marcos", agendamentoSalvo.getCliente());

        verify(agendamentoRepository).save(agendamento);
    }

    @Test
    void naoDeveSalvarQuandoHorarioJaEstiverPreenchido() {

        Agendamento agendamento = criarAgendamento();

        when(agendamentoRepository
                .findByServicoAndDataHoraAgendamentoBetween(
                        any(),
                        any(),
                        any()))
                .thenReturn(new Agendamento());

        RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        agendamentoService.salvarAgendamento(agendamento));

        assertEquals("Horário já está preenchido",
                exception.getMessage());

        verify(agendamentoRepository, never())
                .save(any());
    }
    
    @Test
    void deveDeletarAgendamento() {

        LocalDateTime data =
                LocalDateTime.of(2026,7,10,14,0);

        doNothing().when(agendamentoRepository)
                .deleteByDataHoraAgendamentoAndCliente(
                        data,
                        "Marcos");

        agendamentoService.deletarAgendamento(data,"Marcos");

        verify(agendamentoRepository)
                .deleteByDataHoraAgendamentoAndCliente(
                        data,
                        "Marcos");
    }

    @Test
    void deveBuscarAgendamentosDoDia() {

        LocalDate data = LocalDate.of(2026,7,10);

        List<Agendamento> lista = List.of(
                criarAgendamento(),
                criarAgendamento()
        );

        when(agendamentoRepository
                .findByDataHoraAgendamentoBetween(any(), any()))
                .thenReturn(lista);

        List<Agendamento> resultado =
                agendamentoService.buscarAgendamentosDia(data);

        assertEquals(2, resultado.size());

        verify(agendamentoRepository)
                .findByDataHoraAgendamentoBetween(any(), any());
    }

    @Test
    void deveAlterarAgendamento() {

        Agendamento existente = criarAgendamento();

        Agendamento novo = criarAgendamento();
        novo.setCliente("Carlos");

        when(agendamentoRepository
                .findByDataHoraAgendamentoAndCliente(
                        any(),
                        any()))
                .thenReturn(existente);

        when(agendamentoRepository.save(any()))
                .thenReturn(novo);

        Agendamento resultado =
                agendamentoService.alterarAgendamento(
                        novo,
                        "Marcos",
                        existente.getDataHoraAgendamento());

        assertEquals("Carlos",
                resultado.getCliente());

        verify(agendamentoRepository)
                .save(any());
    }
}
