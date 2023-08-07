export interface IProject {
  id: string;
  name?: string | null;
  clientName?: string | null;
  isUpsale?: boolean | null;
  country?: string | null;
  clientCompany?: string | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
