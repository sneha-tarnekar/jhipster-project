import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClientComponent } from './list/client.component';
import { ClientDetailComponent } from './detail/client-detail.component';
import ClientResolve from './route/client-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const clientRoute: Routes = [
  {
    path: '',
    component: ClientComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClientDetailComponent,
    resolve: {
      client: ClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default clientRoute;
