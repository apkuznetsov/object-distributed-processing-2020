package com.lab4.PharmacyUi;

import com.lab4.PharmacyDb.Daos.DoctorDao;
import com.lab4.PharmacyDb.Dtos.Doctor;
import com.lab4.PharmacyDb.Dtos.DoctorWithSpecializationName;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;
import java.util.List;

import static com.lab4.MainUI.pharmacyDbDao;

public class DoctorWithSpecializationNameView extends VerticalLayout implements View {
    private DoctorDao doctorDao;

    private Grid<DoctorWithSpecializationName> grid;
    private HorizontalLayout gridLayout;

    public DoctorWithSpecializationNameView() {
        // region поля
        doctorDao = pharmacyDbDao.getDoctorDao();

        grid = new Grid<>(DoctorWithSpecializationName.class);
        gridLayout = new HorizontalLayout(grid);
        // endregion

        setDoctorsGrid();
        gridLayout.setSizeFull();
        gridLayout.setExpandRatio(grid, 1);

        addComponents(gridLayout);
        updateDoctorsGrid();
    }

    private void setDoctorsGrid() {
        grid.setColumns(
                Doctor.ID,
                Doctor.SURNAME,
                Doctor.FORENAME,
                Doctor.PATRONYMIC,
                DoctorWithSpecializationName.SPECIALIZATION_NAME);

        grid.getColumn(Doctor.ID).setCaption("Номер");
        grid.getColumn(Doctor.SURNAME).setCaption("Фамилия");
        grid.getColumn(Doctor.FORENAME).setCaption("Имя");
        grid.getColumn(Doctor.PATRONYMIC).setCaption("Отчество");
        grid.getColumn(DoctorWithSpecializationName.SPECIALIZATION_NAME).setCaption("Специализация");

        grid.setSizeFull();
    }

    public void updateDoctorsGrid() {
        try {
            List<DoctorWithSpecializationName> doctors = doctorDao.getAllDoctorsWithSpecializationName();
            grid.setItems(doctors);
        } catch (SQLException | ClassNotFoundException exc) {
            Notification.show(exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
