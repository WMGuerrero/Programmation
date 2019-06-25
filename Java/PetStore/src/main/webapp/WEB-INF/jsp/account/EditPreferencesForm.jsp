<%@ include file="../common/IncludeTop.jsp"%>

<div id="Catalog"><stripes:form
	beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
	focus="">

	<%@ include file="IncludePreferencesFields.jsp"%>

	<stripes:submit name="editAccount" value="Save" />

</stripes:form>

<%@ include file="../common/IncludeBottom.jsp"%>
