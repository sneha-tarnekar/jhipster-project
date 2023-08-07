export interface IClient {
  id: string;
  clientName?: string | null;
  clientCountry?: string | null;
  clientCompany?: string | null;
  companyUrl?: string | null;
  companyWebsite?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
