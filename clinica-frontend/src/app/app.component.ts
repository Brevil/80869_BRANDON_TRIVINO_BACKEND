import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MODULOS } from './core/modulos.config';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <aside class="lateral">
      <div class="marca">🐾 Clínica</div>
      <nav>
        @for (m of modulos; track m.ruta) {
          <a [routerLink]="'/' + m.ruta" routerLinkActive="activo">{{ m.titulo }}</a>
        }
      </nav>
    </aside>
    <main class="contenido"><router-outlet /></main>
  `,
  styles: `
    :host { display: flex; min-height: 100vh; }
    .lateral { width: 270px; flex-shrink: 0; background: #14213d; color: #fff; padding: 18px 0; }
    .marca { font-size: 1.2rem; font-weight: 700; padding: 0 20px 16px; }
    nav { display: flex; flex-direction: column; }
    nav a { color: #cdd6ea; text-decoration: none; padding: 10px 20px; font-size: .92rem; border-left: 3px solid transparent; }
    nav a:hover { background: #1d2f55; color: #fff; }
    nav a.activo { background: #1f3a70; color: #fff; border-left-color: #4c9aff; }
    .contenido { flex: 1; min-width: 0; background: #f5f7fb; }
    @media (max-width: 800px) { :host { flex-direction: column; } .lateral { width: auto; } }
  `,
})
export class AppComponent {
  readonly modulos = MODULOS;
}
