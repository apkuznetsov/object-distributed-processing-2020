package com.lab4.PharmacyUi.Windows;

import com.lab4.MainUI;
import com.lab4.PharmacyDb.Daos.DoctorSpecializationDao;
import com.lab4.PharmacyDb.Dtos.DoctorSpecialization;
import com.lab4.PharmacyUi.DoctorSpecializationView;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

import static com.lab4.MainUI.pharmacyDbDao;

public class DoctorSpecializationWindow extends Window {
    private VerticalLayout mainLayout;
    private TextField name;
    private Button saveBtn;
    private Button cancelBtn;
    private HorizontalLayout buttons;

    private DoctorSpecializationDao doctorSpecializationDao;
    private DoctorSpecialization doctorSpecialization;

    private MainUI mainUi;
    private DoctorSpecializationView doctorSpecializationView;
    private Binder<DoctorSpecialization> binder;

    public DoctorSpecializationWindow(MainUI mainUi, DoctorSpecializationView doctorSpecializationView) {
        mainLayout = new VerticalLayout();
        name = new TextField("Название");
        saveBtn = new Button("ОК");
        cancelBtn = new Button("Отменить");
        buttons = new HorizontalLayout(saveBtn, cancelBtn);
        doctorSpecializationDao = pharmacyDbDao.getDoctorSpecializationDao();
        this.mainUi = mainUi;
        this.doctorSpecializationView = doctorSpecializationView;
        binder = new Binder<>(DoctorSpecialization.class);

        binder.bindInstanceFields(this);

        setSizeUndefined();
        setModal(true);
        setResizable(false);

        mainLayout.addComponents(name, buttons);
        setContent(mainLayout);

        setSaveBtn();
        setCancelBtn();
    }

    public void setDoctorSpecialization(DoctorSpecialization doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
        binder.setBean(doctorSpecialization);

        setVisible(true);
        name.selectAll();
    }

    private void setSaveBtn() {
        saveBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        saveBtn.addClickListener(event -> {
            try {
                saveDoctorSpecializationDtoIntoDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show(exc.getMessage());
                exc.printStackTrace();
            }
        });
    }

    public void saveDoctorSpecializationDtoIntoDb() throws SQLException, ClassNotFoundException {
        updateDoctorSpecializationDto();

        if (doctorSpecialization.isPersisted()) {
            doctorSpecializationDao.updateDoctorSpecialization(doctorSpecialization);
        } else {
            doctorSpecializationDao.insertDoctorSpecialization(doctorSpecialization);
        }

        doctorSpecializationView.updateDoctorSpecializationsGrid();
        setVisible(false);
    }

    private void updateDoctorSpecializationDto() {
        doctorSpecialization.setName(name.getValue());
    }

    private void setCancelBtn() {
        cancelBtn.addClickListener(event -> {
            close();
        });
    }

    public void deleteDoctorSpecializationDtoFromDb() throws SQLException, ClassNotFoundException {
        doctorSpecializationDao.deleteDoctorSpecialization(doctorSpecialization.getId());
        doctorSpecializationView.updateDoctorSpecializationsGrid();
        setVisible(false);
    }
}
