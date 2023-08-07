import { Gender } from 'app/entities/enumerations/gender.model';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: '7c1c96b4-a524-4b72-8a18-5512a691c3fd',
};

export const sampleWithPartialData: IEmployee = {
  id: '217ebba9-4d05-4777-862c-521e8d85e580',
  name: 'Hybrid sunt',
  employeeId: 16112,
  gender: 'MALE',
};

export const sampleWithFullData: IEmployee = {
  id: '2250135a-f2ba-4875-85fe-ca23298f2b64',
  name: 'workforce alarm connect',
  employeeId: 6231,
  age: 31474,
  gender: 'MALE',
};

export const sampleWithNewData: NewEmployee = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
