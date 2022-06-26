import Sequelize from 'sequelize';

import { DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD } from '../constants/secrets.js';

const sequelize = new Sequelize(DB_NAME, DB_USER, DB_PASSWORD, {
    host: DB_HOST,
    dialect: 'postgres',
    port: DB_PORT,
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