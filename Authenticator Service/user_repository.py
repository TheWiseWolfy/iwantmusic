from user_orm import User
from sql_base import Session

def get_users():
    session = Session()
    users = session.query(User).all()
    return users


def create_user(username, password):
    session = Session()
    user = User(username, password)

    try:
        session.add(user)
        session.commit()
    except Exception as exc:
        print(f"Failed to add user - {exc}")

    return user

def get_user_by_username(username: str):
    session = Session()
    user = session.query(User).filter(User.username == username).first()
    return user