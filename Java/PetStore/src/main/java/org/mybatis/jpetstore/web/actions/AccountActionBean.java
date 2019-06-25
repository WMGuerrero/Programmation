package org.mybatis.jpetstore.web.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.service.AccountService;
import org.mybatis.jpetstore.service.CatalogService;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

@SessionScope
public class AccountActionBean
    extends AbstractActionBean {

    private static final List<String> CATEGORY_LIST;

    private static final String EDIT_ACCOUNT = "/WEB-INF/jsp/account/EditAccountForm.jsp";

    private static final String EDIT_PREFERENCES = "/WEB-INF/jsp/account/EditPreferencesForm.jsp";

    private static final String EDIT_USER_INFORMATION =
        "/WEB-INF/jsp/account/EditUserInformationForm.jsp";

    private static final List<String> LANGUAGE_LIST;

    private static final String NEW_ACCOUNT = "/WEB-INF/jsp/account/NewAccountForm.jsp";

    private static final long serialVersionUID = 5499663666155758178L;

    private static final String SIGNON = "/WEB-INF/jsp/account/SignonForm.jsp";

    private Account account = new Account();

    @SpringBean
    private transient AccountService accountService;

    private boolean authenticated;

    @SpringBean
    private transient CatalogService catalogService;

    private List<Product> myList;

    static {
        List<String> langList = new ArrayList<>();
        langList.add("english");
        langList.add("japanese");
        LANGUAGE_LIST = Collections.unmodifiableList(langList);

        List<String> catList = new ArrayList<>();
        catList.add("FISH");
        catList.add("DOGS");
        catList.add("REPTILES");
        catList.add("CATS");
        catList.add("BIRDS");
        CATEGORY_LIST = Collections.unmodifiableList(catList);
    }

    public void clear() {

        this.account = new Account();
        this.myList = null;
        this.authenticated = false;
    }

    public Resolution editAccount() {

        this.accountService.updateAccount(this.account);
        this.account = this.accountService.getAccount(this.account.getUsername());
        this.myList =
            this.catalogService.getProductListByCategory(this.account.getFavouriteCategoryId());
        return new RedirectResolution(CatalogActionBean.class);
    }

    public Resolution editAccountForm() {

        return new ForwardResolution(AccountActionBean.EDIT_ACCOUNT);
    }

    public Resolution editPreferencesForm() {

        return new ForwardResolution(AccountActionBean.EDIT_PREFERENCES);
    }

    public Resolution editUserInformationForm() {

        return new ForwardResolution(AccountActionBean.EDIT_USER_INFORMATION);
    }

    public Account getAccount() {

        return this.account;
    }

    public List<String> getCategories() {

        return AccountActionBean.CATEGORY_LIST;
    }

    public List<String> getLanguages() {

        return AccountActionBean.LANGUAGE_LIST;
    }

    public List<Product> getMyList() {

        return this.myList;
    }

    public String getPassword() {

        return this.account.getPassword();
    }

    public String getUsername() {

        return this.account.getUsername();
    }

    public boolean isAuthenticated() {

        return this.authenticated && this.account != null && this.account.getUsername() != null;
    }

    public Resolution newAccount() {

        this.accountService.insertAccount(this.account);
        this.account = this.accountService.getAccount(this.account.getUsername());
        this.myList =
            this.catalogService.getProductListByCategory(this.account.getFavouriteCategoryId());
        this.authenticated = true;
        return new RedirectResolution(CatalogActionBean.class);
    }

    public Resolution newAccountForm() {

        return new ForwardResolution(AccountActionBean.NEW_ACCOUNT);
    }

    public void setMyList(
        final List<Product> myList) {

        this.myList = myList;
    }

    @Validate(required = true, on = { "signon", "newAccount", "editAccount" })
    public void setPassword(
        final String password) {

        this.account.setPassword(password);
    }

    @Validate(required = true, on = { "signon", "newAccount", "editAccount" })
    public void setUsername(
        final String username) {

        this.account.setUsername(username);
    }

    public Resolution signoff() {

        this.context.getRequest()
            .getSession()
            .invalidate();
        clear();
        return new RedirectResolution(CatalogActionBean.class);
    }

    public Resolution signon() {

        this.account = this.accountService.getAccount(getUsername(), getPassword());

        if (this.account == null) {
            String value = "Invalid username or password.  Signon failed.";
            setMessage(value);
            clear();
            return new ForwardResolution(AccountActionBean.SIGNON);
        } else {
            this.account.setPassword(null);
            this.myList =
                this.catalogService.getProductListByCategory(this.account.getFavouriteCategoryId());
            this.authenticated = true;
            HttpSession s =
                this.context.getRequest()
                    .getSession();
            // this bean is already registered as /actions/Account.action
            s.setAttribute("accountBean", this);
            return new RedirectResolution(CatalogActionBean.class);
        }
    }

    @DefaultHandler
    public Resolution signonForm() {

        return new ForwardResolution(AccountActionBean.SIGNON);
    }

}
