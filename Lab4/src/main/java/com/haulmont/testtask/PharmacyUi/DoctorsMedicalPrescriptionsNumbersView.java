package com.haulmont.testtask.PharmacyUi;

import com.haulmont.testtask.PharmacyDb.Daos.DoctorDao;
import com.haulmont.testtask.PharmacyDb.Dtos.DoctorMedicalPrescriptionsNumber;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;
import java.util.List;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;
import static com.haulmont.testtask.PharmacyDb.Dtos.DoctorMedicalPrescriptionsNumber.*;

public class DoctorsMedicalPrescriptionsNumbersView extends VerticalLayout implements View {
    private DoctorDao doctorDao;

    private Grid<DoctorMedicalPrescriptionsNumber> grid;
    private HorizontalLayout gridLayout;

    public DoctorsMedicalPrescriptionsNumbersView() {
        // region поля
        doctorDao = pharmacyDbDao.getDoctorDao();

        grid = new Grid<>(DoctorMedicalPrescriptionsNumber.class);
        gridLayout = new HorizontalLayout(grid);
        // endregion

        setGrid();
        gridLayout.setSizeFull();
        gridLayout.setExpandRatio(grid, 1);

        addComponents(gridLayout);
        updateDoctorsGrid();
    }

    private void setGrid() {
        grid.setColumns(
                ID,
                SURNAME,
                FORENAME,
                PATRONYMIC,
                PRESCRIPTIONS_NUMBER);

        grid.getColumn(ID).setCaption("Номер врача");
        grid.getColumn(SURNAME).setCaption("Фамилия");
        grid.getColumn(FORENAME).setCaption("Имя");
        grid.getColumn(PATRONYMIC).setCaption("Отчество");
        grid.getColumn(PRESCRIPTIONS_NUMBER).setCaption("Кол-во рецептов");

        grid.setSizeFull();
    }

    private void updateDoctorsGrid() {
        try {
            List<DoctorMedicalPrescriptionsNumber> numbers;
            numbers = doctorDao.getAllDoctorsMedicalPrescriptionsNumbers();

            grid.setItems(numbers);
        } catch (SQLException | ClassNotFoundException exc) {
            Notification.show(exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
