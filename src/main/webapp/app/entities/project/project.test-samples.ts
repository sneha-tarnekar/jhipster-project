import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: '0003db68-1df7-4f89-9307-0fba9560590f',
};

export const sampleWithPartialData: IProject = {
  id: '652cbe17-6fc2-423a-9785-ac3cea867051',
  clientName: 'South Convertible',
  clientCompany: 'aboard calculating withdrawal',
};

export const sampleWithFullData: IProject = {
  id: '337af1cd-52e7-41a8-bbb0-7f2d38b8c9d6',
  name: 'Cotton',
  clientName: 'below Chicken Frozen',
  isUpsale: true,
  country: 'Sweden',
  clientCompany: 'Uruguayo Stage',
};

export const sampleWithNewData: NewProject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
