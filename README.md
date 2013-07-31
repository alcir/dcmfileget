dcmfileget
==========

Dump some DICOM attributes in this form

``filepath|PatientName|Date|patientid|studyid``

It is based on dcmche-2.0.27 toolkit and inspired by dcm2txt tool.



Usage
----------

``./bin/dcmfileget.sh /path/tofile.dcm``

To extract attributes from a single file.


Or you can use

``./bin/dcmfileget.sh /path/todir``

To scan the directory and extract attributes for each file.
