import { Gender } from 'app/entities/enumerations/gender.model';

export interface IEmployee {
  id: string;
  name?: string | null;
  employeeId?: number | null;
  age?: number | null;
  gender?: keyof typeof Gender | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
