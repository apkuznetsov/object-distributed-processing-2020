package com.haulmont.testtask.PharmacyUi;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.PharmacyDb.Daos.DoctorSpecializationDao;
import com.haulmont.testtask.PharmacyDb.Dtos.DoctorSpecialization;
import com.haulmont.testtask.PharmacyUi.Windows.DoctorSpecializationWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

import static com.haulmont.testtask.MainUI.pharmacyDbDao;

public class DoctorSpecializationView extends VerticalLayout implements View {
    private MainUI mainUi;

    private DoctorSpecializationDao doctorSpecializationDao;

    private TextField filterTf;
    private Button clearFilterTfBtn;
    private CssLayout filteringLayout;

    private Button addDoctorSpecializationBtn;
    private HorizontalLayout toolbarLayout;

    private Grid<DoctorSpecialization> doctorSpecializationsGrid;
    private HorizontalLayout gridLayout;

    private Button updateDoctorSpecializationBtn;
    private Button deleteDoctorSpecializationBtn;
    private HorizontalLayout buttonsLayout;

    private DoctorSpecialization selectedDoctorSpecialization;

    public DoctorSpecializationView(MainUI mainUi) {
        // region поля
        this.mainUi = mainUi;

        doctorSpecializationDao = pharmacyDbDao.getDoctorSpecializationDao();

        filterTf = new TextField();
        clearFilterTfBtn = new Button(VaadinIcons.CLOSE);
        filteringLayout = new CssLayout(filterTf, clearFilterTfBtn);

        addDoctorSpecializationBtn = new Button("Добавить специализацию");
        toolbarLayout = new HorizontalLayout(filteringLayout, addDoctorSpecializationBtn);

        doctorSpecializationsGrid = new Grid<>(DoctorSpecialization.class);
        gridLayout = new HorizontalLayout(doctorSpecializationsGrid);

        updateDoctorSpecializationBtn = new Button("Изменить");
        deleteDoctorSpecializationBtn = new Button("Удалить");
        buttonsLayout = new HorizontalLayout(updateDoctorSpecializationBtn, deleteDoctorSpecializationBtn);
        // endregion

        setFilterTextField();
        setClearFilterTfBtn();
        filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setAddDoctorSpecializationBtn();

        setDoctorSpecializationsGrid();
        gridLayout.setSizeFull();
        gridLayout.setExpandRatio(doctorSpecializationsGrid, 1);

        setButtons();

        addComponents(toolbarLayout, gridLayout, buttonsLayout);
        updateDoctorSpecializationsGrid();
    }

    private void setFilterTextField() {
        filterTf.setPlaceholder("Поиск по названию...");
        filterTf.addValueChangeListener(event -> updateDoctorSpecializationsGrid());
        filterTf.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void setClearFilterTfBtn() {
        clearFilterTfBtn.setDescription("Очистить фильтр");
        clearFilterTfBtn.addClickListener(event -> filterTf.clear());
    }

    private void setAddDoctorSpecializationBtn() {
        addDoctorSpecializationBtn.addClickListener(event -> {
            doctorSpecializationsGrid.asSingleSelect().clear();

            DoctorSpecializationWindow doctorSpecializationWindow = new DoctorSpecializationWindow(mainUi, this);
            mainUi.addWindow(doctorSpecializationWindow);
            doctorSpecializationWindow.setVisible(true);

            doctorSpecializationWindow.setDoctorSpecialization(new DoctorSpecialization());
        });
    }

    private void setDoctorSpecializationsGrid() {
        doctorSpecializationsGrid.setColumns(
                DoctorSpecialization.ID,
                DoctorSpecialization.NAME);

        doctorSpecializationsGrid.getColumn(DoctorSpecialization.ID).setCaption("Номер");
        doctorSpecializationsGrid.getColumn(DoctorSpecialization.NAME).setCaption("Название");

        doctorSpecializationsGrid.setSizeFull();

        doctorSpecializationsGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                setButtonsInvisible();
            } else {
                updateDoctorSpecializationBtn.setVisible(true);
                deleteDoctorSpecializationBtn.setVisible(true);
                selectedDoctorSpecialization = event.getValue();
            }
        });
    }

    private void setButtonsInvisible() {
        doctorSpecializationsGrid.asSingleSelect().clear();
        updateDoctorSpecializationBtn.setVisible(false);
        deleteDoctorSpecializationBtn.setVisible(false);
    }

    private void setButtons() {
        setButtonsInvisible();

        updateDoctorSpecializationBtn.addClickListener(event -> {
            DoctorSpecializationWindow doctorSpecializationWindow = new DoctorSpecializationWindow(mainUi, this);
            mainUi.addWindow(doctorSpecializationWindow);
            doctorSpecializationWindow.setDoctorSpecialization(selectedDoctorSpecialization);
        });

        deleteDoctorSpecializationBtn.addClickListener(event -> {
            try {
                DoctorSpecializationWindow doctorSpecializationWindow = new DoctorSpecializationWindow(mainUi, this);
                mainUi.addWindow(doctorSpecializationWindow);
                doctorSpecializationWindow.close();

                doctorSpecializationWindow.setDoctorSpecialization(selectedDoctorSpecialization);
                doctorSpecializationWindow.deleteDoctorSpecializationDtoFromDb();
            } catch (SQLException | ClassNotFoundException exc) {
                Notification.show("Невозможно удалить специализацию");
                exc.printStackTrace();
            } finally {
                setButtonsInvisible();
                selectedDoctorSpecialization = null;
            }
        });
    }

    public void updateDoctorSpecializationsGrid() {
        try {
            List<DoctorSpecialization> doctorSpecializations;

            String searchSurname = filterTf.getValue();
            if (searchSurname.equals("")) {
                doctorSpecializations = doctorSpecializationDao.getAllDoctorSpecializations();
            } else {
                doctorSpecializations = doctorSpecializationDao.getDoctorSpecializationsByName(searchSurname);
            }

            doctorSpecializationsGrid.setItems(doctorSpecializations);
        } catch (SQLException | ClassNotFoundException exc) {
            Notification.show(exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
