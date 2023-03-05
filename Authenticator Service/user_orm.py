from sqlalchemy import Column, String, Integer
from sql_base import Base
from sqlalchemy.orm import relationship

class User(Base):
    __tablename__ = 'users'

    id = Column (Integer, primary_key=True)
    username = Column(String)
    password = Column(String)
    role = Column(Integer)  #0 - admin, 1- artist, 2 - user


    def __init__(self ,username, password):
        self.username = username
        self.password = password