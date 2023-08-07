import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployee, NewEmployee } from '../employee.model';

export type PartialUpdateEmployee = Partial<IEmployee> & Pick<IEmployee, 'id'>;

export type EntityResponseType = HttpResponse<IEmployee>;
export type EntityArrayResponseType = HttpResponse<IEmployee[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employee: NewEmployee): Observable<EntityResponseType> {
    return this.http.post<IEmployee>(this.resourceUrl, employee, { observe: 'response' });
  }

  update(employee: IEmployee): Observable<EntityResponseType> {
    return this.http.put<IEmployee>(`${this.resourceUrl}/${this.getEmployeeIdentifier(employee)}`, employee, { observe: 'response' });
  }

  partialUpdate(employee: PartialUpdateEmployee): Observable<EntityResponseType> {
    return this.http.patch<IEmployee>(`${this.resourceUrl}/${this.getEmployeeIdentifier(employee)}`, employee, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmployeeIdentifier(employee: Pick<IEmployee, 'id'>): string {
    return employee.id;
  }

  compareEmployee(o1: Pick<IEmployee, 'id'> | null, o2: Pick<IEmployee, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmployeeIdentifier(o1) === this.getEmployeeIdentifier(o2) : o1 === o2;
  }

  addEmployeeToCollectionIfMissing<Type extends Pick<IEmployee, 'id'>>(
    employeeCollection: Type[],
    ...employeesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const employees: Type[] = employeesToCheck.filter(isPresent);
    if (employees.length > 0) {
      const employeeCollectionIdentifiers = employeeCollection.map(employeeItem => this.getEmployeeIdentifier(employeeItem)!);
      const employeesToAdd = employees.filter(employeeItem => {
        const employeeIdentifier = this.getEmployeeIdentifier(employeeItem);
        if (employeeCollectionIdentifiers.includes(employeeIdentifier)) {
          return false;
        }
        employeeCollectionIdentifiers.push(employeeIdentifier);
        return true;
      });
      return [...employeesToAdd, ...employeeCollection];
    }
    return employeeCollection;
  }
}
