package com.lab4;

import com.lab4.PharmacyDb.PharmacyDbDao;
import com.lab4.PharmacyDb.PharmacyDbDaoFactory;
import com.lab4.PharmacyUi.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    public static final String STATISTICS_VIEW = "Статистика";
    private static final String DB_URL = "jdbc:hsqldb:file:testdb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";
    private static final String PATIENT_VIEW = "Пациенты";
    private static final String DOCTOR_VIEW = "Врачи";
    private static final String DOCTOR_WITH_SPECIALIZATION_NAME_VIEW = "Врачи и спец.";
    private static final String DOCTOR_SPECIALIZATIONS_VIEW = "Специализации";
    private static final String MEDICAL_PRESCRIPTIONS_VIEW = "Рецепты";
    public static PharmacyDbDao pharmacyDbDao = PharmacyDbDaoFactory.createInstance(DB_URL, USER, PASSWORD);
    private HorizontalLayout mainLayout;

    private Label title;
    private Button patientViewBtn;
    private Button doctorViewBtn;
    private Button doctorWithSpecializationNameViewBtn;
    private Button doctorSpecializationViewBtn;
    private Button medicalPrescriptionViewBtn;
    private VerticalLayout menu;
    private VerticalLayout viewContainer;

    @Override
    protected void init(VaadinRequest request) {
        title = new Label("Меню");
        title.addStyleName(ValoTheme.MENU_TITLE);

        patientViewBtn = new Button(PATIENT_VIEW,
                event -> getNavigator().navigateTo(PATIENT_VIEW));
        patientViewBtn.addStyleName(ValoTheme.MENU_ITEM);

        doctorViewBtn = new Button(DOCTOR_VIEW,
                event -> getNavigator().navigateTo(DOCTOR_VIEW));
        doctorViewBtn.addStyleName(ValoTheme.MENU_ITEM);

        doctorWithSpecializationNameViewBtn = new Button(DOCTOR_WITH_SPECIALIZATION_NAME_VIEW,
                event -> getNavigator().navigateTo(DOCTOR_WITH_SPECIALIZATION_NAME_VIEW));
        doctorWithSpecializationNameViewBtn.addStyleName(ValoTheme.MENU_ITEM);

        doctorSpecializationViewBtn = new Button(DOCTOR_SPECIALIZATIONS_VIEW,
                event -> getNavigator().navigateTo(DOCTOR_SPECIALIZATIONS_VIEW));
        doctorSpecializationViewBtn.addStyleName(ValoTheme.MENU_ITEM);

        medicalPrescriptionViewBtn = new Button(MEDICAL_PRESCRIPTIONS_VIEW,
                event -> getNavigator().navigateTo(MEDICAL_PRESCRIPTIONS_VIEW));
        medicalPrescriptionViewBtn.addStyleName(ValoTheme.MENU_ITEM);

        menu = new VerticalLayout(title,
                patientViewBtn,
                doctorViewBtn,
                doctorWithSpecializationNameViewBtn,
                doctorSpecializationViewBtn,
                medicalPrescriptionViewBtn);
        menu.addStyleName(ValoTheme.MENU_ROOT);

        viewContainer = new VerticalLayout();

        mainLayout = new HorizontalLayout(menu, viewContainer);
        mainLayout.setSizeFull();
        viewContainer.setSizeFull();
        mainLayout.setExpandRatio(menu, 3);
        mainLayout.setExpandRatio(viewContainer, 15);
        setContent(mainLayout);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", new DefaultView());
        navigator.addView(PATIENT_VIEW, new PatientView(this));
        navigator.addView(DOCTOR_VIEW, new DoctorView(this));
        navigator.addView(DOCTOR_WITH_SPECIALIZATION_NAME_VIEW, new DoctorWithSpecializationNameView());
        navigator.addView(DOCTOR_SPECIALIZATIONS_VIEW, new DoctorSpecializationView(this));
        navigator.addView(MEDICAL_PRESCRIPTIONS_VIEW, new MedicalPrescriptionView(this));
        navigator.addView(STATISTICS_VIEW, new DoctorsMedicalPrescriptionsNumbersView());
    }
}