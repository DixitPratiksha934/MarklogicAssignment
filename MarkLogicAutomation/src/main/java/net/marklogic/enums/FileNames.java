package net.marklogic.enums;

public enum FileNames {

  TestDataRelativePath("src/test/resources/testdata/"),
  UserCarerProperties("UserCarer"),
  UserTenantAdminProperties("UserTenantAdmin");

  private String value;

  FileNames(String value) {
    this.value = value;
  }

  public String toString() {
    return value;
  }
}
