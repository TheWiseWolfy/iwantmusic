from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import  sessionmaker

Base = declarative_base()
Engine = create_engine('mariadb+mariadbconnector://user_service:user_service@127.0.0.1:3306/db_iwantmusic_users')

Session = sessionmaker( bind=Engine)