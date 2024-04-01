package springbook.user.dao;

public class MassageDao {
    
    private ConnectionMaker connectionMaker;

    public MassageDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
