package com.haulmont.testtask.PharmacyUi;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.PatientDao;
import com.haulmont.testtask.PharmacyDb.Dtos.Patient;
import com.haulmont.testtask.PharmacyUi.Windows.PatientWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class PatientView extends VerticalLayout implements View {
    private MainUI mainUi;

    private PatientDao patientDao;

    private TextField filterTf;
    private Button clearFilterTfBtn;
    private CssLayout filteringLayout;

    private Button addPatientBtn;
    private HorizontalLayout toolbarLayout;

    private Grid<Patient> patientsGrid;
    private HorizontalLayout gridLayout;

    private Button updatePatientBtn;
    private Button deletePatientBtn;
    private HorizontalLayout buttonsLayout;

    private Patient selectedPatient;

    public PatientView(MainUI mainUi) {
        // region поля
        this.mainUi = mainUi;

        patientDao = pharmacyDbDao.getPatientDao();

        filterTf = new TextField();
        clearFilterTfBtn = new Button(VaadinIcons.CLOSE);
        filteringLayout = new CssLayout(filterTf, clearFilterTfBtn);

        addPatientBtn = new Button("Добавить пациента");
        toolbarLayout = new HorizontalLayout(filteringLayout, addPatientBtn);

        patientsGrid = new Grid<>(Patient.class);
        gridLayout = new HorizontalLayout(patientsGrid);

        updatePatientBtn = new Button("Изменить");
        deletePatientBtn = new Button("Удалить");
        buttonsLayout = new HorizontalLayout(updatePatientBtn, deletePatientBtn);
        // endregion

        setFilterTextField();
        setClearFilterTfBtn();
        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setAddPatientBtn();

        setPatientsGrid();
        gridLayout.setSizeFull();
        gridLayout.setExpandRatio(patientsGrid, 1);

        setButtons();

        addComponents(toolbarLayout, gridLayout, buttonsLayout);
        updatePatientsGrid();
    }

    private void setFilterTextField() {
        filterTf.setPlaceholder("Поиск по фамилии...");
        filterTf.addValueChangeListener(event -> updatePatientsGrid());
        filterTf.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void setClearFilterTfBtn() {
        clearFilterTfBtn.setDescription("Очистить фильтр");
        clearFilterTfBtn.addClickListener(event -> filterTf.clear());
    }

    private void setAddPatientBtn() {
        addPatientBtn.addClickListener(event -> {
            patientsGrid.asSingleSelect().clear();

            PatientWindow patientWindow = new PatientWindow(mainUi, this);
            mainUi.addWindow(patientWindow);
            patientWindow.setVisible(true);

            patientWindow.setPatient(new Patient());
        });
    }

    private void setPatientsGrid() {
        patientsGrid.setColumns(
                Patient.ID,
                Patient.SURNAME,
                Patient.FORENAME,
                Patient.PATRONYMIC,
                Patient.PHONE);

        patientsGrid.getColumn(Patient.ID).setCaption("Номер");
        patientsGrid.getColumn(Patient.SURNAME).setCaption("Фамилия");
        patientsGrid.getColumn(Patient.FORENAME).setCaption("Имя");
        patientsGrid.getColumn(Patient.PATRONYMIC).setCaption("Отчество");
        patientsGrid.getColumn(Patient.PHONE).setCaption("Телефон");

        patientsGrid.setSizeFull();

        patientsGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                setButtonsInvisible();
            } else {
                updatePatientBtn.setVisible(true);
                deletePatientBtn.setVisible(true);
                selectedPatient = event.getValue();
            }
        });
    }

    private void setButtonsInvisible() {
        patientsGrid.asSingleSelect().clear();
        updatePatientBtn.setVisible(false);
        deletePatientBtn.setVisible(false);
    }

    private void setButtons() {
        setButtonsInvisible();

        updatePatientBtn.addClickListener(event -> {
            PatientWindow patientWindow = new PatientWindow(mainUi, this);
            mainUi.addWindow(patientWindow);
            patientWindow.setPatient(selectedPatient);
        });

        deletePatientBtn.addClickListener(event -> {
            try {
                PatientWindow patientWindow = new PatientWindow(mainUi, this);
                mainUi.addWindow(patientWindow);
                patientWindow.close();

                patientWindow.setPatient(selectedPatient);
                patientWindow.deletePatientDtoFromDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show("Невозможно удалить пациента");
                exc.printStackTrace();
            } finally {
                setButtonsInvisible();
                selectedPatient = null;
            }
        });
    }

    public void updatePatientsGrid() {
        try {
            List<Patient> patients;

            String searchSurname = filterTf.getValue();
            if (searchSurname.equals("")) {
                patients = patientDao.getAllPatients();
            } else {
                patients = patientDao.getPatientsBySurname(searchSurname);
            }

            patientsGrid.setItems(patients);
        } catch (SQLException | ClassNotFoundException exc) {
            Notification.show(exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
