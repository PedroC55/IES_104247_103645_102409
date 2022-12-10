FROM python:3
COPY requirements.txt ./
RUN pip install -r requirements.txt
ADD data_generator.py /
CMD ["python3", "-u", "data_generator.py" ]
