package br.purpletech.vivo.utils;

import br.purpletech.vivo.dtos.user.*;
import br.purpletech.vivo.dtos.chat.*;
import br.purpletech.vivo.dtos.message.*;
import br.purpletech.vivo.dtos.onboarding.*;
import br.purpletech.vivo.dtos.platform.*;
import br.purpletech.vivo.dtos.report.*;
import br.purpletech.vivo.dtos.step.*;
import br.purpletech.vivo.dtos.task.*;
import br.purpletech.vivo.dtos.team.*;
import br.purpletech.vivo.models.*;

import java.util.List;

public class EntityDtoConverter {
    // 🛠️ User
    public static User toUser(UserToCreateDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setPosition(dto.position());
        user.setTelephone(dto.telephone());
        user.setRole(dto.role());
        return user;
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPosition(),
                user.getTelephone(),
                user.getRole(),
                user.getTeam().getName(),
                (List<Onboarding>) user.getOnboarding(),
                user.getReports()
        );
    }

    //Team
    public static Team toTeam(TeamToCreateDTO dto) {
        Team team = new Team();
        team.setName(dto.name());
        team.setDepartment(dto.department());
        return team;
    }

    public static TeamDTO toTeamDTO(Team team) {
        List<PlatformDTO> platforms = team.getPlatforms().stream()
                .map(EntityDtoConverter::toPlatformDTO)
                .toList();

        List<UserDTO> users = team.getUsers().stream()
                .map(EntityDtoConverter::toUserDTO)
                .toList();

        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getDepartment(),
                platforms,
                users
        );
    }

    //Step
    public static StepDTO toStepDTO(Step step) {
        return new StepDTO(
                step.getId(),
                step.getName(),
                step.getDescription(),
                toOnboardingDTO(step.getOnboarding()),
                step.getTasks().stream()
                        .map(EntityDtoConverter::toTaskDTO)
                        .toList()
        );
    }

    public static Step toStep(StepToCreateDTO dto) {
        Step step = new Step();
        step.setName(dto.name());
        step.setDescription(dto.description());
        return step;
    }

    //Task
    public static TaskDTO toTaskDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                toStepDTO(task.getStep()),
                task.isStandard()
        );
    }

    public static Task toTask(TaskToCreateDTO dto) {
        Task task = new Task();
        task.setName(dto.name());
        task.setStandard(dto.standard());
        return task;
    }

    public static PlatformDTO toPlatformDTO(Platform platform) {
        return new PlatformDTO(
                platform.getId(),
                platform.getName(),
                platform.getType_access(),
                platform.getUrl()
        );
    }

    public static Platform toPlatform(PlatformToCreateDTO dto) {
        Platform platform = new Platform();
        platform.setName(dto.name());
        platform.setType_access(dto.type_access());
        platform.setUrl(dto.url());
        return platform;
    }

    public static OnboardingDTO toOnboardingDTO(Onboarding onboarding) {
        return new OnboardingDTO(
                onboarding.getId(),
                onboarding.getDt_begin(),
                onboarding.getDt_end(),
                onboarding.isActive(),
                toUserDTO(onboarding.getManager()),
                toUserDTO(onboarding.getBuddy()),
                toUserDTO(onboarding.getCollaborator()),
                onboarding.getSteps().stream()
                        .map(EntityDtoConverter::toStepDTO)
                        .toList(),
                onboarding.getReports().stream()
                        .map(EntityDtoConverter::toReportDTO)
                        .toList()
        );
    }

    public static Onboarding toOnboarding(OnboardingToCreateDTO dto) {
        Onboarding onboarding = new Onboarding();
        onboarding.setDt_begin(dto.dt_begin());
        onboarding.setDt_end(dto.dt_end());
        onboarding.setActive(dto.active());
        return onboarding;
    }

    public static ReportDTO toReportDTO(Report report) {
        return new ReportDTO(
                report.getId(),
                report.getCreatedAt(),
                report.getFeeling(),
                report.getQuestion(),
                report.getEvent(),
                report.getComment()
        );
    }

    public static Report toReport(ReportToCreateDTO dto) {
        Report report = new Report();
        report.setFeeling(dto.feeling());
        report.setQuestion(dto.question());
        report.setEvent(dto.event());
        report.setComment(dto.comment());
        return report;
    }

    public static MessageDTO toMessageDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                message.getTime(),
                message.getSender().getName()
        );
    }

    public static Message toMessage(MessageToCreateDTO dto) {
        Message message = new Message();
        message.setContent(dto.content());
        return message;
    }

    public static ChatDTO toChatDTO(Chat chat) {
        return new ChatDTO(
                chat.getId(),
                chat.getParticipants().stream()
                                .map(EntityDtoConverter::toUserDTO)
                                .toList(),
                chat.getMessages().stream()
                        .map(EntityDtoConverter::toMessageDTO)
                        .toList()
        );
    }

}
