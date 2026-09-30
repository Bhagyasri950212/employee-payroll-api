````markdown
# Employee Management & Payroll API - ER Diagram

```mermaid
erDiagram

    USERS {
        BIGINT id PK
        VARCHAR username UK
        VARCHAR password
        VARCHAR role
    }

    EMPLOYEES {
        BIGINT id PK
        VARCHAR name
        VARCHAR email UK
        VARCHAR phone
        VARCHAR department
        VARCHAR designation
        DATE joining_date
        VARCHAR employment_status
        DATETIME created_at
        DATETIME updated_at
    }

    PAYROLL {
        BIGINT id PK
        BIGINT employee_id FK
        DECIMAL basic_salary
        DECIMAL allowances
        DECIMAL deductions
        DECIMAL net_salary
        VARCHAR pay_month
    }

    EMPLOYEES ||--o{ PAYROLL : "has"
````

## Relationships

### Employees → Payroll

One employee can have multiple payroll records.

```text
EMPLOYEES (1) -------- (Many) PAYROLL
```

The `employee_id` in the `PAYROLL` table is a foreign key referencing the `id` of the `EMPLOYEES` table.

### Users

The `USERS` table stores authentication information.

Each user has one role:

* ADMIN
* EMPLOYEE

The `password` is stored as a BCrypt-hashed password.

```
