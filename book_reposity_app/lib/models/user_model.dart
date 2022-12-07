class UserModel {
  final String username;
  final String password;
  final bool isAuthor;
  final bool isPublisher;
  final bool isBookstore;

  UserModel(this.username, this.password, this.isAuthor, this.isPublisher,
      this.isBookstore);
}
