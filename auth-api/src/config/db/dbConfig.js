import Sequelize from 'sequelize';

const sequelize = new Sequelize('auth-db', 'postgres', '153111', {
    host: 'localhost',
    dialect: 'postgres',
    port: 5434,
    quoteIdentifiers: false,
    define: {
      syncOnAssociation: true,
      timestamps: false,
      underscored: true,
      underscoredAll: true,
      freezeTableName: true,
    },    
});

sequelize
.authenticate()
.then(() => {
    console.log('Connection has been stablished!');
})
.catch((err) => {
    console.error('Unsable to connect to the database');
    console.error(err);
})

export default sequelize;