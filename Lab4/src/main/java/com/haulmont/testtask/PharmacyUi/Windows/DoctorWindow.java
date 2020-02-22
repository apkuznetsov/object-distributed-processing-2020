package com.haulmont.testtask.PharmacyUi.Windows;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.DoctorDao;
import com.haulmont.testtask.PharmacyDb.Dtos.Doctor;
import com.haulmont.testtask.PharmacyUi.DoctorView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class DoctorWindow extends Window {
    private VerticalLayout mainLayout;
    private TextField forename;
    private TextField patronymic;
    private TextField surname;
    private TextField specializationId;
    private Button saveBtn;
    private Button cancelBtn;
    private HorizontalLayout buttons;

    private DoctorDao doctorDao;
    private Doctor doctor;

    private MainUI mainUi;
    private DoctorView doctorView;

    public DoctorWindow(MainUI mainUi, DoctorView doctorView) {
        mainLayout = new VerticalLayout();
        forename = new TextField("Имя");
        patronymic = new TextField("Отчество");
        surname = new TextField("Фамилия");
        specializationId = new TextField("Номер специализации");
        saveBtn = new Button("ОК");
        cancelBtn = new Button("Отменить");
        buttons = new HorizontalLayout(saveBtn, cancelBtn);
        doctorDao = pharmacyDbDao.getDoctorDao();
        this.mainUi = mainUi;
        this.doctorView = doctorView;

        setSizeUndefined();
        setModal(true);
        setResizable(false);

        mainLayout.addComponents(forename, patronymic, surname, specializationId, buttons);
        setContent(mainLayout);

        setSaveBtn();
        setCancelBtn();
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;

        setFields(doctor);

        setVisible(true);
        forename.selectAll();
    }

    private void setFields(Doctor doctor) {
        forename.setValue(doctor.getForename());
        patronymic.setValue(doctor.getPatronymic());
        surname.setValue(doctor.getSurname());
        specializationId.setValue(Long.toString(doctor.getSpecializationId()));
    }

    private void setSaveBtn() {
        saveBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        saveBtn.addClickListener(event -> {
            try {
                saveDoctorDtoIntoDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show("Невозможно добавить врача");
                exc.printStackTrace();
            }
        });
    }

    public void saveDoctorDtoIntoDb() throws SQLException, ClassNotFoundException {
        updateDoctorDto();

        if (doctor.isPersisted()) {
            doctorDao.updateDoctor(doctor);
        } else {
            doctorDao.insertDoctor(doctor);
        }

        doctorView.updateDoctorsGrid();
        setVisible(false);
    }

    private void updateDoctorDto() {
        doctor.setForename(forename.getValue());
        doctor.setPatronymic(patronymic.getValue());
        doctor.setSurname(surname.getValue());
        doctor.setSpecializationId(Long.parseLong(specializationId.getValue()));
    }

    private void setCancelBtn() {
        cancelBtn.addClickListener(event -> {
            close();
        });
    }

    public void deleteDoctorDtoFromDb() throws SQLException, ClassNotFoundException {
        doctorDao.deleteDoctor(doctor.getId());
        doctorView.updateDoctorsGrid();
        setVisible(false);
    }
}
