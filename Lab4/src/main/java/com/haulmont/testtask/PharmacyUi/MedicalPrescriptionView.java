package com.haulmont.testtask.PharmacyUi;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.haulmont.testtask.PharmacyDb.Dtos.MedicalPrescription;
import com.haulmont.testtask.PharmacyUi.Windows.MedicalPrescriptionWindow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class MedicalPrescriptionView extends VerticalLayout implements View {
    private MainUI mainUi;

    private MedicalPrescriptionDao prescriptionDao;

    private TextField descriptionFilterField;
    private TextField patientIdFilterField;
    private TextField priorityFilterField;
    private Button applyFiltersBtn;
    private CssLayout filteringLayout;

    private Button addPrescriptionBtn;
    private HorizontalLayout toolbarLayout;

    private Grid<MedicalPrescription> prescriptionsGrid;
    private HorizontalLayout gridLayout;

    private Button updatePrescriptionBtn;
    private Button deletePrescriptionBtn;
    private HorizontalLayout buttonsLayout;

    private MedicalPrescription selectedPrescription;

    public MedicalPrescriptionView(MainUI mainUi) {
        // region поля
        this.mainUi = mainUi;

        prescriptionDao = pharmacyDbDao.getMedicalPrescriptionDao();

        descriptionFilterField = new TextField();
        patientIdFilterField = new TextField();
        priorityFilterField = new TextField();
        descriptionFilterField.setPlaceholder("Фильтр. по описанию");
        patientIdFilterField.setPlaceholder("по номеру пациента");
        priorityFilterField.setPlaceholder("по приоритету");
        applyFiltersBtn = new Button("Поиск", event -> updateMedicalPrescriptionsGrid());
        filteringLayout = new CssLayout(
                descriptionFilterField,
                patientIdFilterField,
                priorityFilterField,
                applyFiltersBtn);

        addPrescriptionBtn = new Button("Добавить рецепт");
        toolbarLayout = new HorizontalLayout(filteringLayout, addPrescriptionBtn);

        prescriptionsGrid = new Grid<>(MedicalPrescription.class);
        gridLayout = new HorizontalLayout(prescriptionsGrid);

        updatePrescriptionBtn = new Button("Изменить");
        deletePrescriptionBtn = new Button("Удалить");
        buttonsLayout = new HorizontalLayout(updatePrescriptionBtn, deletePrescriptionBtn);
        // endregion

        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setAddPrescriptionBtn();

        setPrescriptionsGrid();
        gridLayout.setSizeFull();
        gridLayout.setExpandRatio(prescriptionsGrid, 1);

        setButtons();

        addComponents(toolbarLayout, gridLayout, buttonsLayout);
        updateMedicalPrescriptionsGrid();
    }

    private void setAddPrescriptionBtn() {
        addPrescriptionBtn.addClickListener(event -> {
            prescriptionsGrid.asSingleSelect().clear();

            MedicalPrescriptionWindow prescriptionWindow = new MedicalPrescriptionWindow(mainUi, this);
            mainUi.addWindow(prescriptionWindow);
            prescriptionWindow.setVisible(true);

            prescriptionWindow.setMedicalPrescription(new MedicalPrescription());
        });
    }

    private void setPrescriptionsGrid() {
        prescriptionsGrid.setColumns(
                MedicalPrescription.DESCRIPTION,
                MedicalPrescription.PATIENT_ID,
                MedicalPrescription.DOCTOR_ID,
                MedicalPrescription.CREATION_DATE,
                MedicalPrescription.VALIDITY_DATE,
                MedicalPrescription.PRIORITY);

        prescriptionsGrid.getColumn(MedicalPrescription.DESCRIPTION).setCaption("Описание");
        prescriptionsGrid.getColumn(MedicalPrescription.PATIENT_ID).setCaption("Номер пациента");
        prescriptionsGrid.getColumn(MedicalPrescription.DOCTOR_ID).setCaption("Номер врача");
        prescriptionsGrid.getColumn(MedicalPrescription.CREATION_DATE).setCaption("Дата создания");
        prescriptionsGrid.getColumn(MedicalPrescription.VALIDITY_DATE).setCaption("Срок действия");
        prescriptionsGrid.getColumn(MedicalPrescription.PRIORITY).setCaption("Приоритет");

        prescriptionsGrid.setSizeFull();

        prescriptionsGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                setButtonsInvisible();
            } else {
                updatePrescriptionBtn.setVisible(true);
                deletePrescriptionBtn.setVisible(true);
                selectedPrescription = event.getValue();
            }
        });
    }

    private void setButtonsInvisible() {
        prescriptionsGrid.asSingleSelect().clear();
        updatePrescriptionBtn.setVisible(false);
        deletePrescriptionBtn.setVisible(false);
    }

    private void setButtons() {
        setButtonsInvisible();

        updatePrescriptionBtn.addClickListener(event -> {
            MedicalPrescriptionWindow prescriptionWindow = new MedicalPrescriptionWindow(mainUi, this);
            mainUi.addWindow(prescriptionWindow);
            prescriptionWindow.setMedicalPrescription(selectedPrescription);
        });

        deletePrescriptionBtn.addClickListener(event -> {
            try {
                MedicalPrescriptionWindow prescriptionWindow = new MedicalPrescriptionWindow(mainUi, this);
                mainUi.addWindow(prescriptionWindow);
                prescriptionWindow.close();

                prescriptionWindow.setMedicalPrescription(selectedPrescription);
                prescriptionWindow.deleteMedicalPrescriptionDtoFromDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show("Невозможно удалить рецепт");
                exc.printStackTrace();
            } finally {
                setButtonsInvisible();
                selectedPrescription = null;
            }
        });
    }

    public void updateMedicalPrescriptionsGrid() {
        try {
            List<MedicalPrescription> prescriptions;

            String searchDescription = descriptionFilterField.getValue();

            long searchPatientId;
            if (patientIdFilterField.getValue().equals("")) {
                searchPatientId = -1L;
            } else {
                searchPatientId = Long.parseLong(patientIdFilterField.getValue());
            }

            byte searchPriority;
            if (priorityFilterField.getValue().equals("")) {
                searchPriority = 0;
            } else {
                searchPriority = Byte.parseByte(priorityFilterField.getValue());
            }

            if (searchDescription.equals("")) {
                prescriptions = prescriptionDao.getAllMedicalPrescriptions();
            } else {
                prescriptions = prescriptionDao.getMedicalPrescriptionsByFilters(searchDescription, searchPatientId, searchPriority);
            }

            prescriptionsGrid.setItems(prescriptions);
        } catch (SQLException | ClassNotFoundException exc) {
            Notification.show("Невозможно обновить рецепт");
            exc.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
