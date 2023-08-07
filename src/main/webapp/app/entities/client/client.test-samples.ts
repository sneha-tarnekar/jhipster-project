import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: '64caa487-cb2d-441a-bc9c-f6d44b5e464d',
};

export const sampleWithPartialData: IClient = {
  id: 'f3079bf0-b914-46ed-bec7-09cd467c90c5',
  clientCountry: 'bourgeoisie Island emulation',
  companyUrl: 'payment programming teal',
};

export const sampleWithFullData: IClient = {
  id: '216f1bbc-9223-458c-8771-1c701c70a4e9',
  clientName: 'plume Volkswagen',
  clientCountry: 'Elegant',
  clientCompany: 'Cab',
  companyUrl: 'Focused salmon',
  companyWebsite: 'East',
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
