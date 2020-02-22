package com.haulmont.testtask.PharmacyUi.Windows;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.PatientDao;
import com.haulmont.testtask.PharmacyDb.Dtos.Patient;
import com.haulmont.testtask.PharmacyUi.PatientView;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class PatientWindow extends Window {
    private VerticalLayout mainLayout;
    private TextField forename;
    private TextField patronymic;
    private TextField surname;
    private TextField phone;
    private Button saveBtn;
    private Button cancelBtn;
    private HorizontalLayout buttons;

    private PatientDao patientDao;
    private Patient patient;

    private MainUI mainUi;
    private PatientView patientView;
    private Binder<Patient> binder;

    public PatientWindow(MainUI mainUi, PatientView patientView) {
        mainLayout = new VerticalLayout();
        forename = new TextField("Имя");
        patronymic = new TextField("Отчество");
        surname = new TextField("Фамилия");
        phone = new TextField("Телефон");
        saveBtn = new Button("ОК");
        cancelBtn = new Button("Отменить");
        buttons = new HorizontalLayout(saveBtn, cancelBtn);
        patientDao = pharmacyDbDao.getPatientDao();
        this.mainUi = mainUi;
        this.patientView = patientView;
        binder = new Binder<>(Patient.class);

        binder.bindInstanceFields(this);

        setSizeUndefined();
        setModal(true);
        setResizable(false);

        mainLayout.addComponents(forename, patronymic, surname, phone, buttons);
        setContent(mainLayout);

        setSaveBtn();
        setCancelBtn();
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        binder.setBean(patient);

        setVisible(true);
        forename.selectAll();
    }

    private void setSaveBtn() {
        saveBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        saveBtn.addClickListener(event -> {
            try {
                savePatientDtoIntoDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show(exc.getMessage());
                exc.printStackTrace();
            }
        });
    }

    public void savePatientDtoIntoDb() throws SQLException, ClassNotFoundException {
        updatePatientDto();

        if (patient.isPersisted()) {
            patientDao.updatePatient(patient);
        } else {
            patientDao.insertPatient(patient);
        }

        patientView.updatePatientsGrid();
        setVisible(false);
    }

    private void updatePatientDto() {
        patient.setForename(forename.getValue());
        patient.setPatronymic(patronymic.getValue());
        patient.setSurname(surname.getValue());
        patient.setPhone(phone.getValue());
    }

    private void setCancelBtn() {
        cancelBtn.addClickListener(event -> {
            close();
        });
    }

    public void deletePatientDtoFromDb() throws SQLException, ClassNotFoundException {
        patientDao.deletePatient(patient.getId());
        patientView.updatePatientsGrid();
        setVisible(false);
    }
}
