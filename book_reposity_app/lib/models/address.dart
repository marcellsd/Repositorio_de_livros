class Address {
  String id;
  String hqAddress;
  String webSiteAddress;

  Address(this.id, this.hqAddress, this.webSiteAddress);

  factory Address.fromJson(Map<String, dynamic> json) {
    return Address(
        json["id"].toString(), json["hqAddress"], json["webSiteAddress"]);
  }

  Map<String, dynamic> toJson() {
    return {"hqAddress": hqAddress, "webSiteAddress": webSiteAddress};
  }
}
