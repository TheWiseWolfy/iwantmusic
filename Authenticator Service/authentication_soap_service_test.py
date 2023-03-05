# python -m pip install suds
from suds.client import Client
c = Client('http://localhost:8070/?wsdl')

token = c.service.authenticate("unwise", "1234")
print(token)

print(c.service.validate_token("unwise",token ))
