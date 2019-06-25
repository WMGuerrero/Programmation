<%@ include file="../common/IncludeTop.jsp"%>

<div id="Catalog"><stripes:form
	beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
	focus="">

	<div>
		<stripes:link
			beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
			event="editUserInformationForm">
      		Change Password
      	</stripes:link>
	</div>

	<%@ include file="IncludeAccountFields.jsp"%>

	<stripes:submit name="editAccount" value="Save" />

</stripes:form>

<%@ include file="../common/IncludeBottom.jsp"%>
