package my.project.dto.abm;

public record CustomerForReportDTO(
        String tdId,
        String tdName,
        String tdDocument,
        String tdTelephone,
        String tdAddress,
        String tdStatus
) {
    @Override
    public String tdId() {
        return tdId;
    }

    @Override
    public String tdName() {
        return tdName;
    }

    @Override
    public String tdDocument() {
        return tdDocument;
    }

    @Override
    public String tdTelephone() {
        return tdTelephone;
    }

    @Override
    public String tdAddress() {
        return tdAddress;
    }

    @Override
    public String tdStatus() {
        return tdStatus;
    }
}
