import Sequelize from 'sequelize';

import { DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD } from '../constants/secrets.js';

const sequelize = new Sequelize(DB_NAME, DB_USER, DB_PASSWORD, {
    host: DB_HOST,
    port: DB_PORT,
    dialect: "postgres",
    quoteIdentifiers: false,
    define: {
      syncOnAssociation: true,
      timestamps: false,
      underscored: true,
      underscoredAll: true,
      freezeTableName: true,
    },
    pool: {
      acquire: 180000,
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