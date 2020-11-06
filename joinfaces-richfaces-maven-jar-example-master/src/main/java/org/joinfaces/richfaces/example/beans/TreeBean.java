/*
package org.joinfaces.richfaces.example.beans;

import org.joinfaces.richfaces.example.model.CD;
import org.joinfaces.richfaces.example.model.Company;
import org.joinfaces.richfaces.example.model.Country;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Named
@ApplicationScoped
public class TreeBean implements Serializable {

    @Inject
    private CDParser cdParser;

    private List<CDXmlDescriptor> cdXmlDescriptors;
    private List<TreeNode> rootNodes = new ArrayList<TreeNode>();
    private Map<String, Country> countriesCache = new HashMap<String, Country>();
    private Map<String, Company> companiesCache = new HashMap<String, Company>();
    private TreeNode currentSelection = null;

    @PostConstruct
    public void init() {

        this.cdXmlDescriptors = this.cdParser.getCdsList();
        for (CDXmlDescriptor current : cdXmlDescriptors)
        {
            String countryName = current.getCountry();
            String companyName = current.getCompany();
            Country country = getCountryByName(current);
            Company company = getCompanyByName(current, country);
            CD cd = new CD(current.getTitle(), current.getArtist(), company, current.getPrice(), current.getYear());
            company.getCds().add(cd);
        }
    }

    public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {
        // considering only single selection
        List<Object> selection = new ArrayList<Object>(selectionChangeEvent.getNewSelection());
        Object currentSelectionKey = selection.get(0);
        UITree tree = (UITree) selectionChangeEvent.getSource();

        Object storedKey = tree.getRowKey();
        tree.setRowKey(currentSelectionKey);
        currentSelection = (TreeNode) tree.getRowData();
        tree.setRowKey(storedKey);
    }

    private Country getCountryByName(CDXmlDescriptor descriptor) {
        String countryName = descriptor.getCountry();
        Country country = countriesCache.get(countryName);
        if (country == null) {
            country = new Country();
            country.setName(countryName);
            countriesCache.put(countryName, country);
            rootNodes.add(country);
        }
        return country;
    }

    private Company getCompanyByName(CDXmlDescriptor descriptor, Country country) {
        String companyName = descriptor.getCompany();
        Company company = companiesCache.get(companyName);
        if (company == null) {
            company = new Company();
            company.setName(companyName);
            company.setParent(country);
            country.getCompanies().add(company);
            companiesCache.put(companyName, company);
        }
        return company;
    }

    public List<CDXmlDescriptor> getCdXmlDescriptors() {
        return cdXmlDescriptors;
    }

    public void setCdXmlDescriptors(List<CDXmlDescriptor> cdXmlDescriptors) {
        this.cdXmlDescriptors = cdXmlDescriptors;
    }

    public List<TreeNode> getRootNodes() {
        return rootNodes;
    }

    public void setRootNodes(List<TreeNode> rootNodes) {
        this.rootNodes = rootNodes;
    }

    public TreeNode getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(TreeNode currentSelection) {
        this.currentSelection = currentSelection;
    }

}
*/