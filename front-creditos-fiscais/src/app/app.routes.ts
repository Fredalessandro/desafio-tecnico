import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { GuestGuard } from './guards/guest.guard';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent),
        canActivate: [GuestGuard]
    },
    {
        path: 'cadastro',
        loadComponent: () => import('./auth/cadastro/cadastro.component').then(m => m.CadastroComponent),
        canActivate: [GuestGuard]
    },
    {
        path: 'credito',
        loadChildren: () => import('./credito/credito.routes').then(m => m.CREDITO_ROUTES),
        canActivate: [AuthGuard]
    },
    {
        path: '',
        redirectTo: 'credito',
        pathMatch: 'full'
    }
];
