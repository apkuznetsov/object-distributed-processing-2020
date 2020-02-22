package com.haulmont.testtask.PharmacyUi;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.DoctorDao;
import com.haulmont.testtask.PharmacyDb.Dtos.Doctor;
import com.haulmont.testtask.PharmacyUi.Windows.DoctorWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

import static com.haulmont.testtask.MainUI.STATISTICS_VIEW;
import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class DoctorView extends VerticalLayout implements View {
    private MainUI mainUi;

    private DoctorDao doctorDao;

    private TextField filterTf;
    private Button clearFilterTfBtn;
    private CssLayout filteringLayout;

    private Button addDoctorBtn;
    private Button showStatisticsBtn;
    private HorizontalLayout toolbarLayout;

    private Grid<Doctor> doctorsGrid;
    private HorizontalLayout gridLayout;

    private Button updateDoctorBtn;
    private Button deleteDoctorBtn;
    private HorizontalLayout buttonsLayout;

    private Doctor selectedDoctor;

    public DoctorView(MainUI mainUi) {
        // region поля
        this.mainUi = mainUi;

        doctorDao = pharmacyDbDao.getDoctorDao();

        filterTf = new TextField();
        clearFilterTfBtn = new Button(VaadinIcons.CLOSE);
        filteringLayout = new CssLayout(filterTf, clearFilterTfBtn);

        addDoctorBtn = new Button("Добавить доктора");
        showStatisticsBtn = new Button("Показать статистику",
                event -> mainUi.getNavigator().navigateTo(STATISTICS_VIEW));
        toolbarLayout = new HorizontalLayout(filteringLayout, addDoctorBtn, showStatisticsBtn);

        doctorsGrid = new Grid<>(Doctor.class);
        gridLayout = new HorizontalLayout(doctorsGrid);

        updateDoctorBtn = new Button("Изменить");
        deleteDoctorBtn = new Button("Удалить");
        buttonsLayout = new HorizontalLayout(updateDoctorBtn, deleteDoctorBtn);
        // endregion

        setFilterTextField();
        setClearFilterTfBtn();
        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setAddDoctorBtn();

        setDoctorsGrid();
        gridLayout.setSizeFull();
        gridLayout.setExpandRatio(doctorsGrid, 1);

        setButtons();

        addComponents(toolbarLayout, gridLayout, buttonsLayout);
        updateDoctorsGrid();
    }

    private void setFilterTextField() {
        filterTf.setPlaceholder("Поиск по фамилии...");
        filterTf.addValueChangeListener(event -> updateDoctorsGrid());
        filterTf.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void setClearFilterTfBtn() {
        clearFilterTfBtn.setDescription("Очистить фильтр");
        clearFilterTfBtn.addClickListener(event -> filterTf.clear());
    }

    private void setAddDoctorBtn() {
        addDoctorBtn.addClickListener(event -> {
            doctorsGrid.asSingleSelect().clear();

            DoctorWindow doctorWindow = new DoctorWindow(mainUi, this);
            mainUi.addWindow(doctorWindow);
            doctorWindow.setVisible(true);

            doctorWindow.setDoctor(new Doctor());
        });
    }

    private void setDoctorsGrid() {
        doctorsGrid.setColumns(
                Doctor.ID,
                Doctor.SURNAME,
                Doctor.FORENAME,
                Doctor.PATRONYMIC,
                Doctor.SPECIALIZATION_ID);

        doctorsGrid.getColumn(Doctor.ID).setCaption("Номер");
        doctorsGrid.getColumn(Doctor.SURNAME).setCaption("Фамилия");
        doctorsGrid.getColumn(Doctor.FORENAME).setCaption("Имя");
        doctorsGrid.getColumn(Doctor.PATRONYMIC).setCaption("Отчество");
        doctorsGrid.getColumn(Doctor.SPECIALIZATION_ID).setCaption("Номер специализации");

        doctorsGrid.setSizeFull();

        doctorsGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                setButtonsInvisible();
            } else {
                updateDoctorBtn.setVisible(true);
                deleteDoctorBtn.setVisible(true);
                selectedDoctor = event.getValue();
            }
        });
    }

    private void setButtonsInvisible() {
        doctorsGrid.asSingleSelect().clear();
        updateDoctorBtn.setVisible(false);
        deleteDoctorBtn.setVisible(false);
    }

    private void setButtons() {
        setButtonsInvisible();

        updateDoctorBtn.addClickListener(event -> {
            DoctorWindow doctorWindow = new DoctorWindow(mainUi, this);
            mainUi.addWindow(doctorWindow);
            doctorWindow.setDoctor(selectedDoctor);
        });

        deleteDoctorBtn.addClickListener(event -> {
            try {
                DoctorWindow doctorWindow = new DoctorWindow(mainUi, this);
                mainUi.addWindow(doctorWindow);
                doctorWindow.close();

                doctorWindow.setDoctor(selectedDoctor);
                doctorWindow.deleteDoctorDtoFromDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show("Невозможно удалить доктора");
                exc.printStackTrace();
            } finally {
                setButtonsInvisible();
                selectedDoctor = null;
            }
        });
    }

    public void updateDoctorsGrid() {
        try {
            List<Doctor> doctors;

            String searchSurname = filterTf.getValue();
            if (searchSurname.equals("")) {
                doctors = doctorDao.getAllDoctors();
            } else {
                doctors = doctorDao.getDoctorsBySurname(searchSurname);
            }

            doctorsGrid.setItems(doctors);
        } catch (SQLException | ClassNotFoundException exc) {
            Notification.show(exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
