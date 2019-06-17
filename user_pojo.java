private class Login {
    private String username;
    private String password;
}

private class Profile {
    private boolean paidSub;

}

private class Asset {

}

private class User {
    private Login login;
    private Profile profile;
    private List<Asset> assets;
    private List<Subscription> subs;
}