package com.haulmont.testtask.PharmacyUi.Windows;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.haulmont.testtask.PharmacyDb.Dtos.MedicalPrescription;
import com.haulmont.testtask.PharmacyUi.MedicalPrescriptionView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class MedicalPrescriptionWindow extends Window {
    private VerticalLayout mainLayout;
    private TextArea description;
    private TextField patientId;
    private TextField doctorId;
    private DateTimeField creationDate;
    private DateTimeField validityDate;
    private TextField priority;
    private Button saveBtn;
    private Button cancelBtn;
    private HorizontalLayout buttons;

    private MedicalPrescriptionDao prescriptionDao;
    private MedicalPrescription prescription;

    private MainUI mainUi;
    private MedicalPrescriptionView prescriptionView;

    public MedicalPrescriptionWindow(MainUI mainUi, MedicalPrescriptionView prescriptionView) {
        mainLayout = new VerticalLayout();
        description = new TextArea("Описание");
        patientId = new TextField("Номер пациента");
        doctorId = new TextField("Номер врача");
        creationDate = new DateTimeField("Дата создания");
        validityDate = new DateTimeField("Срок действия");
        priority = new TextField("Приоритет");
        saveBtn = new Button("ОК");
        cancelBtn = new Button("Отменить");
        buttons = new HorizontalLayout(saveBtn, cancelBtn);
        prescriptionDao = pharmacyDbDao.getMedicalPrescriptionDao();
        this.mainUi = mainUi;
        this.prescriptionView = prescriptionView;

        setSizeUndefined();
        setModal(true);
        setResizable(false);

        mainLayout.addComponents(description,
                patientId, doctorId,
                creationDate, validityDate,
                priority,
                buttons);
        setContent(mainLayout);

        setSaveBtn();
        setCancelBtn();
    }

    public void setMedicalPrescription(MedicalPrescription prescription) {
        this.prescription = prescription;

        setFields(prescription);

        setVisible(true);
        description.selectAll();
    }

    private void setFields(MedicalPrescription prescription) {
        description.setValue(prescription.getDescription());
        patientId.setValue(Long.toString(prescription.getPatientId()));
        doctorId.setValue(Long.toString(prescription.getDoctorId()));
        creationDate.setValue(prescription.getCreationDate().toLocalDateTime());
        validityDate.setValue(prescription.getValidityDate().toLocalDateTime());
        priority.setValue(Byte.toString(prescription.getPriority()));
    }

    private void setSaveBtn() {
        saveBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        saveBtn.addClickListener(event -> {
            try {
                saveMedicalPrescriptionDtoIntoDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show("Невозможно добавить рецепт");
                exc.printStackTrace();
            }
        });
    }

    public void saveMedicalPrescriptionDtoIntoDb() throws SQLException, ClassNotFoundException {
        updateMedicalPrescriptionDto();

        if (prescription.isPersisted()) {
            prescriptionDao.updateMedicalPrescription(prescription);
        } else {
            prescriptionDao.insertMedicalPrescription(prescription);
        }

        prescriptionView.updateMedicalPrescriptionsGrid();
        setVisible(false);
    }

    private void updateMedicalPrescriptionDto() {
        prescription.setDescription(description.getValue());
        prescription.setPatientId(Byte.parseByte(patientId.getValue()));
        prescription.setDoctorId(Byte.parseByte(doctorId.getValue()));
        prescription.setCreationDate(Timestamp.valueOf(creationDate.getValue()));
        prescription.setValidityDate(Timestamp.valueOf(validityDate.getValue()));
        prescription.setPriority(Byte.parseByte(priority.getValue()));
    }

    private void setCancelBtn() {
        cancelBtn.addClickListener(event -> {
            close();
        });
    }

    public void deleteMedicalPrescriptionDtoFromDb() throws SQLException, ClassNotFoundException {
        prescriptionDao.deleteMedicalPrescription(prescription.getId());
        prescriptionView.updateMedicalPrescriptionsGrid();
        setVisible(false);
    }
}
